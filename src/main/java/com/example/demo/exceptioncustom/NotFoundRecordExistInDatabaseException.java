package com.example.demo.exceptioncustom;

public class NotFoundRecordExistInDatabaseException extends RuntimeException{
    public NotFoundRecordExistInDatabaseException(String message){
        super(message);
    }

}
