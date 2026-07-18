package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.extension.ProcessSaveException;
import org.example.flowmanager.model.dto.FlowManagerDto;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowManager {

    private final MinioService minioService;
    private final OutboxManager outboxManager;
    private final RequestToUser requestToUser;

    public void process(MultipartFile file, UUID fileUuid) {
        try {
            FlowManagerDto dtoFileManager = requestToUser.uploadFile(fileUuid, file);
            OutboxTable outboxTable = new OutboxTable(null, dtoFileManager.getUserId(), null, dtoFileManager.getPayload(), null, 0,
                    ConversionStatus.COMPLETED_FILE, FileRunStatus.IN_PROGRESS);
            outboxManager.save(outboxTable);
            //TODO: закончить с outbox и шедулер, диспетчер и кафка
            minioService.saveFileUpload(dtoFileManager.getUserId(), file);
        } catch (RuntimeException e) {
            FlowManagerDto flowManagerDto = new FlowManagerDto();
            flowManagerDto.setConversionStatus(ConversionStatus.FAILED_FILE);
            throw new ProcessSaveException("Error saving uploaded file to Minio: " + e.getMessage());
        }

    }
}
