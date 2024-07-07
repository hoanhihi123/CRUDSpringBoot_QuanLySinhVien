package com.example.demo.exceptioncustom;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
