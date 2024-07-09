package com.example.demo.config;

import com.example.demo.exceptioncustom.DuplicateCodeException;
import com.example.demo.exceptioncustom.NotFoundException;
import com.example.demo.exceptioncustom.NotFoundRecordExistInDatabaseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hai and hoan
 * contorller dùng để bắt lỗi chung cho toàn bộ project
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    // for check duplicate field value
    @ExceptionHandler(DuplicateCodeException.class)
    public ResponseEntity<String> handleDuplicateCodeException(DuplicateCodeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // CONFLICT : lỗi xung đột với tài nguyên hiện tại, chẳng hạn như trùng mã
    }

    // for check exist in DB
    @ExceptionHandler(NotFoundRecordExistInDatabaseException.class)
    public ResponseEntity<String> handleNotRecordExistInDatabaseException(NotFoundRecordExistInDatabaseException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * function xử lý các exception khi đối tượng cần sử dụng bị null
     * @param ex lỗi bắt dc
     * @return phản hồi lỗi 500
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullValueInputException(NullPointerException ex){
        logger.error("Exception occurred: {}, Request Details: {}",ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // for show message error of @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // for check payload khi sử dụng postman
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHTTPMessageNotReadableException(HttpMessageNotReadableException ex){
       if(ex.getCause() instanceof InvalidFormatException){
           return new ResponseEntity<>("Chuyển đổi kiểu dữ liệu từ JSON sang Java không hợp lệ!\nNguyên nhân do: " + ex.getHttpInputMessage(),HttpStatus.CONFLICT);
       }
        return new ResponseEntity<>("Dữ liệu truyền từ Client tới Server trống!",HttpStatus.NOT_FOUND);
    }

    /**
     * function xử lý chung các exception
     * @param ex lỗi bắt dc
     * @return phản hồi lỗi 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex){
        logger.error("Exception occurred: {}, Request Details: {}",ex.getMessage(), ex);
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
