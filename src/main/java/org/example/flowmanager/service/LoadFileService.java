package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.exception.FileUploadException;
import org.example.flowmanager.exception.ProcessSaveException;
import org.example.flowmanager.model.dto.ReplyToUserDto;
import org.example.flowmanager.model.dto.SendConversionDto;
import org.example.flowmanager.model.entity.InboxMessage;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.example.flowmanager.repository.InboxRepository;
import org.example.flowmanager.repository.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoadFileService {
    private final MinioService minioService;
    private final InboxRepository inboxRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final OutboxManager outboxManager;

    public ConversionStatus getStatus(UUID fileId) {
        try {
            OutboxTable outboxTableByUuidIs = outboxRepository.findOutboxTableByUuidIs(fileId);
            return outboxTableByUuidIs.getConversionStatus();
        } catch (Exception e) {
            throw new FileUploadException("Not able to find outbox table by id: " + fileId, e);
        }
    }

    public byte[] getFile(UUID fileId) {
        InboxMessage inboxMessageByFileId = inboxRepository.findInboxMessageByUuid(fileId);
        System.out.println("inboxMessageByFileId = " + inboxMessageByFileId);
        try (InputStream file = minioService.getFile(inboxMessageByFileId.getPayload())) {
            return file.readAllBytes();
        } catch (Exception e) {
            throw new FileUploadException("Not able to read file by id: " + fileId, e);
        }
    }

    public ReplyToUserDto processUploadFile(MultipartFile file) {
        try {
            UUID uuid = UUID.randomUUID();
            SendConversionDto sendConversionDto = minioService.saveFileUpload(uuid, file);
            String payload = objectMapper.writeValueAsString(sendConversionDto);
            OutboxTable outboxTable = new OutboxTable(
                    null,
                    payload,
                    null,
                    0,
                    ConversionStatus.PROGRESS_FILE,
                    FileRunStatus.NEW);
            outboxManager.save(outboxTable);
            return new ReplyToUserDto(
                    outboxTable.getUuid(),
                    outboxTable.getConversionStatus());
        } catch (RuntimeException e) {
            OutboxTable outboxTable = new OutboxTable();
            outboxTable.setConversionStatus(ConversionStatus.FAILED_FILE);
            throw new ProcessSaveException("Error saving uploaded file to Minio: " + e.getMessage());
        }
    }
}
