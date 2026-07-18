package org.example.flowmanager.util;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.extension.IncorrectFileFormat;
import org.example.flowmanager.model.enums.FormatConversion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileGenerationId {
    private final List<FormatConversion> files;


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
        try {
            if (nameFile == null) {
                return null;
            }
            int index = nameFile.lastIndexOf(".");
            if (index != -1 || index != nameFile.length() - 1) {
                String format = nameFile.substring(index + 1);
                return files.stream()
                        .map(FormatConversion::toString)
                        .filter(s -> s.equalsIgnoreCase(format)).findFirst().orElse("");
            }
        } catch (RuntimeException e) {
            throw new IncorrectFileFormat("Incorrect format exception");
        }
        return "";
    }

    public String generateNameFiles(String nameFile) {
        return UUID.randomUUID() + getName(nameFile) + checkFormat(nameFile);
    }
}
