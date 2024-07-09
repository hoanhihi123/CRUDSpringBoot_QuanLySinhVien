package com.example.demo.config;

import com.example.demo.exceptioncustom.DuplicateValueException;
import com.example.demo.exceptioncustom.NotFoundRecordExistInDatabaseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
/*
 * Người tạo : Hoan
 * Mục đích  : Custom các Exception theo trường hợp cần bắt lỗi ngoại lệ trong @RestController
 *
 * */
=======
/**
 * @author hai and hoan
 * contorller dùng để bắt lỗi chung cho toàn bộ project
 */
>>>>>>> d60cff16f6f1bca26d1d1303c8844682d8c16531
@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

<<<<<<< HEAD
=======
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

>>>>>>> d60cff16f6f1bca26d1d1303c8844682d8c16531
    /*
     * Mục đích: Bắt các lỗi liên quan tới giá trị trùng lặp
     * Input   : Truyền vào ngoại lệ DuplicateValueException
     * Output  : Trả về một phản hồi HTTP chứa một chuỗi (String) từ một phương thức của controller
     *
     * */
    @ExceptionHandler(DuplicateValueException.class)
    public ResponseEntity<String> handleDuplicateValueException(DuplicateValueException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // CONFLICT : lỗi xung đột với tài nguyên hiện tại, chẳng hạn như trùng mã
    }

    /*
     * Mục đích: Bắt các lỗi không tồn tại bản ghi nào trong database
     * Input   : Truyền vào ngoại lệ NotFoundRecordExistInDatabaseException
     * Output  : Trả về một phản hồi HTTP chứa một chuỗi (String) từ một phương thức của controller
     *
     * */
    @ExceptionHandler(NotFoundRecordExistInDatabaseException.class)
    public ResponseEntity<String> handleNotRecordExistInDatabaseException(NotFoundRecordExistInDatabaseException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

<<<<<<< HEAD
    /*
     * Mục đích: Bắt các lỗi không tồn tại bản ghi nào trong database
     * Input   : Truyền vào ngoại lệ NotFoundRecordExistInDatabaseException
     * Output  : Trả về một phản hồi HTTP chứa một chuỗi (String) từ một phương thức của controller
     *
     * */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullValueInputException(NullPointerException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
=======
//    /*
//     * Mục đích: Bắt các lỗi không tồn tại bản ghi nào trong database
//     * Input   : Truyền vào ngoại lệ NotFoundRecordExistInDatabaseException
//     * Output  : Trả về một phản hồi HTTP chứa một chuỗi (String) từ một phương thức của controller
//     *
//     * */
//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<String> handleNullValueInputException(NullPointerException ex){
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
>>>>>>> d60cff16f6f1bca26d1d1303c8844682d8c16531

    /*
     * Mục đích: Bắt các lỗi không hợp lệ khi sử dụng các annotation như @NotBlank, @Pattern
     * Input   : Truyền vào ngoại lệ MethodArgumentNotValidException
     * Output  : Trả về một phản hồi HTTP chứa một chuỗi (String) từ một phương thức của controller
     *
     * */
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

    /*
     * Mục đích: kiểm tra lỗi xảy ra trong payload, nơi truyền dữ liệu từ client
     * Input   : Truyền vào ngoại lệ HttpMessageNotReadableException
     * Output  :
     *              Trả về một phản hồi HTTP chứa một chuỗi (String)
     *              + với thông báo "Chuyển đổi dữ liệu JSON sang Java không hợp lệ!"
     *                  khi giá trị truyền vào từ client không phù hợp với thuộc tính trong Object
     *              + với thông báo "Dữ liệu truyền từ Client tới Server trống!"
     *                  khi không truyền giá trị nào từ Client tới Server
     *
     * */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHTTPMessageNotReadableException(HttpMessageNotReadableException ex){
        if(ex.getCause() instanceof InvalidFormatException){
            return new ResponseEntity<>("Chuyển đổi kiểu dữ liệu từ JSON sang Java không hợp lệ!\nNguyên nhân do: " + ex.getHttpInputMessage(),HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Dữ liệu truyền từ Client tới Server trống!",HttpStatus.NOT_FOUND);
    }
}
