package org.example.flowmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.flowmanager.model.enums.ConversionStatus;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReplyToUserDto {
    private UUID uuid;
    private ConversionStatus conversionStatus;
}
