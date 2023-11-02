package ru.clevertec.product.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Throwable ex) {
        super(ex);
    }
}