package com.example.demo.exceptioncustom;

/*
 * Người tạo : Hoan
 * Mục đích  : Định nghĩa 1 class NotFoundRecordExistInDatabaseException
 *              để bắt lỗi không tìm thấy bản ghi nào trong database
 *
 * */
public class NotFoundRecordExistInDatabaseException extends RuntimeException{
    public NotFoundRecordExistInDatabaseException(String message){
        super(message);
    }

}