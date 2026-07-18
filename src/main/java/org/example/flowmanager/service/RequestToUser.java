package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.extension.FileUploadException;
import org.example.flowmanager.model.dto.FlowManagerDto;
import org.example.flowmanager.model.dto.UploadResponseDto;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.example.flowmanager.util.FileGenerationId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RequestToUser {

    private final FileGenerationId fileGenerationId;
    private final ObjectMapper objectMapper;

    public FlowManagerDto uploadFile(UUID  uuid, MultipartFile file) {
        try {
        String payload = objectMapper.writeValueAsString(file);
        return new FlowManagerDto(uuid, UUID.randomUUID(),
                fileGenerationId.generateNameFiles(payload), file.getOriginalFilename(), payload,
                ConversionStatus.COMPLETED_FILE, FileRunStatus.IN_PROGRESS);
        } catch (RuntimeException e) {
             throw new FileUploadException("Error upload file");
        }
    }
}
