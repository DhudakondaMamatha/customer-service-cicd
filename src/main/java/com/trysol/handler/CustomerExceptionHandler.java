package com.trysol.handler;

import com.trysol.handler.exception.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<String> customerException(CustomerException customerException){
        return new ResponseEntity<>(customerException.getMessage(), HttpStatus.NOT_FOUND);
    }

}
