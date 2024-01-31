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
        return this.errorMessage(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,Object>> entityNFEx(EntityNotFoundException e){
        return this.errorMessage(HttpStatus.NOT_FOUND,e.getMessage());
    }

    private ResponseEntity<Map<String,Object>> errorMessage(HttpStatus httpStatus,String message){
        Map<String,Object> body = new HashMap<>();
        body.put("status",String.valueOf(httpStatus.value()));
        body.put("error message",message);
        return new ResponseEntity(body,httpStatus);
    }
}
