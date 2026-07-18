package org.example.flowmanager.extension;

public class IncorrectFileFormat extends RuntimeException {
    public IncorrectFileFormat(String message) {
        super(message);
    }
}
