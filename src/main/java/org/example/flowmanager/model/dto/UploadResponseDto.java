package org.example.flowmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.enums.FileRunStatus;

@AllArgsConstructor
@Getter
@RequiredArgsConstructor
public class UploadResponseDto {
    private FileRunStatus status;
}
