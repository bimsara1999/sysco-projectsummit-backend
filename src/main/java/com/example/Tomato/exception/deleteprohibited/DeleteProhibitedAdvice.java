package com.example.Tomato.exception.deleteprohibited;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DeleteProhibitedAdvice {
    @ResponseBody
    @ExceptionHandler(DeleteProhibited.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,String> exceptionHandler(DeleteProhibited exception){

        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        errorMap.put("status", String.valueOf(HttpStatus.FORBIDDEN.value()));

        return errorMap;
    }
}


