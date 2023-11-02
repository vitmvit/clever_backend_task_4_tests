package ru.clevertec.product.exception;

public class SqlExecuteException extends RuntimeException {

    public SqlExecuteException(Throwable ex) {
        super(ex);
    }
}