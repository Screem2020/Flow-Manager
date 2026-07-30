package org.example.flowmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileUpdateDto {
    private UUID uuid;
    private String fileId;
    private String payload;
}
