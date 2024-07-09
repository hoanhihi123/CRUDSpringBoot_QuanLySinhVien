package com.example.demo.exceptioncustom;

/*
 * Người tạo : Hoan
 * Mục đích  : Định nghĩa 1 class DuplicateValueException để bắt lỗi trùng giá trị
 *
 * */
public class DuplicateValueException extends RuntimeException{
    public DuplicateValueException(String message){
        super(message);
    }

}
