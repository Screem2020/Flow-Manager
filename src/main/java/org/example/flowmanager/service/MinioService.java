package org.example.flowmanager.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.exception.FileGetFromMinioException;
import org.example.flowmanager.exception.MinioSaveException;
import org.example.flowmanager.model.dto.SendConversionDto;
import org.example.flowmanager.util.FileGenerationId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MinioService {
    @Value("${minio.bucket-name}")
    private String bucketName;
    private final MinioClient minioClient;
    private final FileGenerationId fileGenerationId;


    public SendConversionDto saveFileUpload(UUID fileId, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String key = fileGenerationId.generateNameFiles(originalFilename);
        log.info("Saving file {} to bucket {}", originalFilename, bucketName);
        try (InputStream inputStream = file.getInputStream()) {
            log.info(
                    "Upload file: name={}, size={}, contentType={}",
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType()
            );
            minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(key)
                    .stream(inputStream, file.getSize(), -1)
                    .build());
        } catch (Exception ex) {
            throw new MinioSaveException("Error save file in Minio");
        }
        return new SendConversionDto(fileId.toString(), bucketName, key);
    }

    public InputStream getFile(String payload) {
        try {
            log.info("Getting file {} from bucket {}", payload, bucketName);
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(payload)
                            .build()
            );
        } catch (Exception e) {
            throw new FileGetFromMinioException("Could not get object from Minio" + payload,e);
        }
    }
}

