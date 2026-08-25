package org.example.flowmanager.util;

import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.exception.IncorrectFileFormat;
import org.example.flowmanager.model.enums.FormatConversion;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
public class FileGenerationId {

    private String getName(String nameFIle) {
        if (nameFIle == null) {
            return "file";
        }
        int index = nameFIle.lastIndexOf(".");
        if (index != -1) {
            return nameFIle.substring(0, index) + ".";
        }
        return nameFIle + ".";
    }

    private String checkFormat(String nameFile) {
        if (nameFile == null) {
            return null;
        }
        int index = nameFile.lastIndexOf(".");
        if (index != -1 && index != nameFile.length() - 1) {
            String format = nameFile.substring(index + 1);
            log.info("Format: {}", format);
            return Arrays.stream(FormatConversion.values())
                    .map(FormatConversion::name)
                    .filter(s -> s.equalsIgnoreCase(format))
                    .findFirst()
                    .orElseThrow(() -> new IncorrectFileFormat(nameFile));
        }
        return "";
    }

    public String generateNameFiles(String nameFile) {
        return UUID.randomUUID() + "_" + getName(nameFile) + checkFormat(nameFile);
    }
}
