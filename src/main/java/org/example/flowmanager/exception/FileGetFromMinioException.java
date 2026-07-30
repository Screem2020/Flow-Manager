package org.example.flowmanager.exception;

public class FileGetFromMinioException extends RuntimeException {
    public FileGetFromMinioException(String message, Throwable cause) {
        super(message, cause);
    }
}
