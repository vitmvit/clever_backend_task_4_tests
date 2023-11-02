package ru.clevertec.product.exception;

public class ProductsNotFoundException extends RuntimeException {

    public ProductsNotFoundException() {
        super("Products not found");
    }
}