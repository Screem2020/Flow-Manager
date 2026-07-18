package org.example.flowmanager.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.example.flowmanager.extension.MinioSaveException;
import org.example.flowmanager.model.entity.ResultPathEntity;
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


    public ResultPathEntity saveFileUpload(UUID userId, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String keyFile = fileGenerationId.generateNameFiles(originalFilename);
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(keyFile)
                    .object(userId.toString())
                    .stream(inputStream, file.getSize(), -1)
                    .build());
        } catch (Exception ex) {
            throw new MinioSaveException("Error save file in Minio");
        }
        return new ResultPathEntity(null, userId, bucketName, keyFile);
    }
}

