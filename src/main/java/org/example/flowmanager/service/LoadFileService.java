package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.entity.InboxMessage;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.repository.InboxRepository;
import org.example.flowmanager.repository.OutboxRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoadFileService {
    private final MinioService minioService;
    private final InboxRepository inboxRepository;
    private final OutboxRepository outboxRepository;

    public ConversionStatus getStatus(UUID fileId) {
        try {
            OutboxTable outboxTableByUuidIs = outboxRepository.findOutboxTableByUuidIs(fileId);
            return outboxTableByUuidIs.getConversionStatus();
        } catch (Exception e) {
            throw new IllegalArgumentException("Not Found file");
        }
    }

    public byte[] getFile(UUID fileId) {
        InboxMessage inboxMessageByFileId = inboxRepository.findInboxMessageByFileId(fileId);
        try (InputStream file = minioService.getFile(inboxMessageByFileId.getPayload())) {
            return file.readAllBytes();
        } catch (Exception e) {
            throw new IllegalArgumentException("Not found file");
        }
    }
}
