package com.msj.myapp.heart;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}