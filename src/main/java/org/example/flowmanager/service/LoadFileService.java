package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.exception.FileUploadException;
import org.example.flowmanager.model.dto.FileUpdateDto;
import org.example.flowmanager.model.dto.ReplyToUserDto;
import org.example.flowmanager.model.dto.SendConversionDto;
import org.example.flowmanager.model.dto.SubscriptionCacheDto;
import org.example.flowmanager.model.entity.InboxMessage;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.example.flowmanager.repository.InboxRepository;
import org.example.flowmanager.repository.OutboxRepository;
import org.example.flowmanager.service.subscription_process.SubscriptionPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class LoadFileService {
    private final MinioService minioService;
    private final InboxRepository inboxRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final OutboxManager outboxManager;
    private final SubscriptionPolicy subscriptionPolicy;
    private final SubscriptionCacheService subscriptionCacheService;

    public ConversionStatus getStatus(UUID fileId) {
        OutboxTable outboxTableByFileId = outboxRepository.findOutboxTableByFileId(fileId);
        if (outboxTableByFileId == null) {
            throw new FileUploadException("File not found");
        }
        return  outboxTableByFileId.getConversionStatus();
    }

    public InputStream getFile(UUID fileUuid) {
        InboxMessage inboxMessageByFileId = inboxRepository.findByFileId(fileUuid.toString());
        if (inboxMessageByFileId == null) {
            throw new RuntimeException(
                    "File not found in inbox: " + fileUuid
            );
        }
        log.info("inboxMessageByFileId = {}", inboxMessageByFileId);
        FileUpdateDto fileUpdateDto = new FileUpdateDto(
                fileUuid,
                inboxMessageByFileId.getFileId(),
                inboxMessageByFileId.getPayload());
        try {
            return minioService.getFile(fileUpdateDto.getPayload());
        } catch (Exception e) {
            throw new FileUploadException("Not able to read file by id: " + fileUuid);
        }
    }

    @Transactional
    public ReplyToUserDto processUploadFile(MultipartFile file, String login) {
        //результат из редис
        SubscriptionCacheDto subscriptionCacheDto = subscriptionCacheService.checkSubscriptionLogin(login);
        subscriptionPolicy.determiningTariff(subscriptionCacheDto, file);
        UUID uuid = UUID.randomUUID();
        SendConversionDto sendConversionDto = minioService.saveFileUpload(uuid, file);
        try {
            String payload = objectMapper.writeValueAsString(sendConversionDto);
            log.info("payload = {}", payload);
            OutboxTable outboxTable = new OutboxTable(
                    uuid,
                    payload,
                    null,
                    0,
                    ConversionStatus.PROGRESS_FILE,
                    FileRunStatus.NEW);
            outboxManager.save(outboxTable);
            return new ReplyToUserDto(
                    UUID.fromString(sendConversionDto.fileId()),
                    outboxTable.getConversionStatus());
        } catch (Exception e) {
            log.info("processUploadFile error", e);
            OutboxTable outboxTable = new OutboxTable(uuid, null, null, 0, ConversionStatus.FAILED_FILE, FileRunStatus.NEW);
            outboxManager.save(outboxTable);
            return new ReplyToUserDto(UUID.fromString(sendConversionDto.fileId()), ConversionStatus.FAILED_FILE);
        }
    }
}
