package com.maugallo.munify_backend.exception;

public class StorageUnavailableException extends RuntimeException {
    public StorageUnavailableException(String message) {
        super(message);
    }
}
