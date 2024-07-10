package com.example.demo.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * @author hai and hoan
 * contorller dùng để bắt lỗi chung cho toàn bộ project
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * function xử lý các exception khi đối tượng cần sử dụng bị null
     * @param ex lỗi bắt dc
     * @return phản hồi lỗi 400
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullValueInputException(NullPointerException ex){
        logger.error("Exception occurred: {}, Request Details: {}",ex.getMessage(), ex);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * handle exception when internal server error
     * @param e
     * @param request
     * @return object chứa thông tin lỗi
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "500 Response", summary = "Handle exception when internal server error", value = """
                            {
                              "timestamp": "2023-10-19T06:35:52.333+00:00",
                              "status": 500,
                              "path": "/...",
                              "error": "Internal Server Error",
                              "message": "Connection timeout, please try again"
                            }
                            """))})
    })
    public ErrorResponse handleException(Exception e,WebRequest request) {
        logger.error(e.getMessage(),e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=",""));
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }


    /**
     * function xử lý xung đột với tài nguyên hiện tại, chẳng hạn như trùng mã
     *
     * @param ex lỗi bắt dc
     * @return phản hồi lỗi 409
     */
    @ExceptionHandler(DuplicateValueException.class)
    public ResponseEntity<String> handleDuplicateValueException(DuplicateValueException ex) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /**
     * Handle exception when the request not found data
     *
     * @param e
     * @param request
     * @return Object chứa thông tin lỗi
     */
    @ExceptionHandler(NotFoundRecordExistInDatabaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Bad Request",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples =
                    @ExampleObject(name = "404 Response", summary = "Handle exception when resource not found",
                            value = """
                                    {
                                    "timestamp": "2023-10-19T06:07:35.321+00:00",
                                    "status": 404,
                                    "path": "/...",
                                    "error": "Not Found",
                                    "message": "{data} not found"
                                    }
                                    """))})
    })
    public ErrorResponse handleNotRecordExistInDatabaseException(NotFoundRecordExistInDatabaseException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }


    /**
     * function xử lý các validate bằng anotation
     *
     * @param e
     * @param request
     * @return Object chứa thông tin lỗi
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request"
                    , content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(name = "Handle exception when data invalid. (@RequestBody,@RequestParam,@PathVariable)",
                            summary = "Handle Bad Request",
                            value = """
                                    {
                                       "timestamp":"2024-04-07T11:38:56.368+00:00",
                                       "status": 400,
                                       "path": "/...",
                                       "error": "Invalid Payload",
                                       "message": "{data} must be not blank"
                                    }
                                    """))})
    })
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setError("Invalid Payload");
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }


    /**
     * function kiểm tra request của các phương thức POST,PUT,PATCH nếu request không có body hoặc body conflict thì báo lỗi
     *
     * @param e
     * @param request
     * @return Object chứa thông tin lỗi
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE, examples = @ExampleObject(name = "409 Response",
                            summary = "Hanlde exception when input data is conflicted", value = """
                            {
                              "timestamp": "2023-10-19T06:07:35.321+00:00",
                              "status": 409,
                              "path": "/...",
                              "error": "Conflict",
                              "message": "{data} exists, Please try again!"
                            }
                            """))})
    })
    public ErrorResponse handleHTTPMessageNotReadableException(HttpMessageNotReadableException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }
}
