package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.flowmanager.extension.ProcessSaveException;
import org.example.flowmanager.model.dto.FileUploadDto;
import org.example.flowmanager.model.dto.SendConversionDto;
import org.example.flowmanager.model.entity.InboxMessage;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowManager {

    private final MinioService minioService;
    private final OutboxManager outboxManager;
    private final ObjectMapper objectMapper;

    public ConversionStatus processUploadFile(MultipartFile file) {
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
            return outboxTable.getConversionStatus();
            //TODO: диспетчер и кафка
        } catch (RuntimeException e) {
            OutboxTable outboxTable = new OutboxTable();
            outboxTable.setConversionStatus(ConversionStatus.FAILED_FILE);
            throw new ProcessSaveException("Error saving uploaded file to Minio: " + e.getMessage());
        }
    }

    @SneakyThrows
    public byte[] processSendFile() {


    }
}
