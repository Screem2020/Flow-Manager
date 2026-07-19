package org.example.flowmanager.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.example.flowmanager.extension.MinioSaveException;
import org.example.flowmanager.model.dto.SendConversionDto;
import org.example.flowmanager.util.FileGenerationId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


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
        try (InputStream inputStream = file.getInputStream()) {
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
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(payload)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Could not get object from Minio" + payload, e);
        }
    }




}

