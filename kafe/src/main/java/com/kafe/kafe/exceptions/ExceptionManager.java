package com.kafe.kafe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> result = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((x)->{
            result.put(((FieldError)x).getField(),x.getDefaultMessage());
        });
        return new ResponseEntity<>(result , HttpStatus.BAD_REQUEST);
    }

}
