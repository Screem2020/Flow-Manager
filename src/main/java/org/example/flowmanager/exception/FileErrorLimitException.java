package org.example.flowmanager.exception;

public class FileErrorLimitException extends RuntimeException {
    public FileErrorLimitException(String message) {
        super(message);
    }
}
