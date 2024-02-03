package com.example.Order.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvise {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> illegalEx(IllegalArgumentException e){
        return ErrorResponseDto.makeMessage(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,Object>> entityNFEx(EntityNotFoundException e){
        return ErrorResponseDto.makeMessage(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
