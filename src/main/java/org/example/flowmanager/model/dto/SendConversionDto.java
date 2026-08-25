package org.example.flowmanager.model.dto;

public record SendConversionDto(
        String fileId,
        String bucket,
        String keyFile) {}
