package com.ProductTrial.api.exceptions;
public class ProductDetailAlreadyExistsException extends RuntimeException {
    public ProductDetailAlreadyExistsException(String message) {
        super(message);
    }

    public ProductDetailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

