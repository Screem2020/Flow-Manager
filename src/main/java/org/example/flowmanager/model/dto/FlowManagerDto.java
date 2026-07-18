package org.example.flowmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.model.enums.FileRunStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlowManagerDto {
    private UUID uuid;
    private UUID userId;
    private String fileUUid;
    private String nameFile;
    private String payload;
    private ConversionStatus conversionStatus;
    private FileRunStatus fileRunStatus;

    public FlowManagerDto(ConversionStatus conversionStatus) {
        this.conversionStatus = conversionStatus;
    }
}
