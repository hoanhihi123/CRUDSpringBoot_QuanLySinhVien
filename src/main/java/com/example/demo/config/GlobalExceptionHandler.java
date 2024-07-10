package com.example.demo.config;

import com.example.demo.exceptioncustom.DuplicateValueException;
import com.example.demo.exceptioncustom.NotFoundRecordExistInDatabaseException;
import com.example.demo.response.ResponseObject;
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

/**
 * @author hai and hoan
 * contorller dùng để bắt lỗi chung cho toàn bộ project
 * bắt các Exception và chuyển chúng thành các phản hồi HTTP
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    /*
     * Mục đích: Bắt các lỗi không hợp lệ khi sử dụng các annotation như @NotBlank, @Pattern
     * Input   : Truyền vào ngoại lệ MethodArgumentNotValidException
     * Output  : Trả về một phản hồi HTTP chứa một chuỗi (String) từ một phương thức của controller
     *
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        logger.error("Exception occurred: {}, Request Details: {}",ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .object(errors).build());
    }


    /**
     *
     * @param ex : Truyền vào ngoại lệ HttpMessageNotReadableException
     * @return :
     *               Trả về một phản hồi HTTP chứa một chuỗi (String)
     *              + với thông báo "Chuyển đổi dữ liệu JSON sang Java không hợp lệ!"
     *                  khi giá trị truyền vào từ client không phù hợp với thuộc tính trong Object
     *              + với thông báo "Payload tại postman trống dữ liệu truyền vào!"
     *                  khi không truyền giá trị nào truyền vào payload tại postman
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseObject> handleHTTPMessageNotReadableException(HttpMessageNotReadableException ex){
        if(ex.getCause() instanceof InvalidFormatException){

            logger.error("Exception occurred: {}, Request Details: {}",ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ResponseObject.builder()
                            .message("Chuyển đổi kiểu dữ liệu từ JSON sang Java không hợp lệ!")
                            .status(HttpStatus.CONFLICT)  // 409: conflict
                            .build()
            );
        }

        logger.error("Exception occurred: {}, Request Details: {}",ex.getMessage(), ex);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseObject.builder()
                        .message("Payload tại postman trống dữ liệu truyền vào!")
                        .status(HttpStatus.BAD_REQUEST)  // 400: bad request
                        .build());
    }

    // for check duplicate field value
    @ExceptionHandler(DuplicateValueException.class)
    public ResponseEntity<ResponseObject> handleDuplicateCodeException(DuplicateValueException ex){
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseObject.builder()
                        .status(HttpStatus.CONFLICT)
                        .build());
    }

    // for check exist in DB
    @ExceptionHandler(NotFoundRecordExistInDatabaseException.class)
    public ResponseEntity<ResponseObject> handleNotRecordExistInDatabaseException(NotFoundRecordExistInDatabaseException ex){
       return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ResponseObject.builder()
                        .status(HttpStatus.NO_CONTENT)
                        .build());
    }
}
