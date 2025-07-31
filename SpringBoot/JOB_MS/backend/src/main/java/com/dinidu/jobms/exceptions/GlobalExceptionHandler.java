package com.dinidu.jobms.exceptions;

import com.dinidu.jobms.utility.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Exception>> handleGenericException(Exception e) {
        return new ResponseEntity<>(
                new ApiResponse<>(
                        500,
                        "An unexpected error occurred: " + e.getMessage(),
                        null
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                new ApiResponse<>(
                        404,
                        e.getMessage(),
                        null
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicateJobException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicateJob(DuplicateJobException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(
                        409,
                        ex.getMessage(),
                        null
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException e){
        Map<String,String> errors=new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError ->{
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(new ApiResponse<>(
                400,
                "Validation Failed",errors)
                , HttpStatus.BAD_REQUEST
        );
    }
}
