package com.example.Tomato.exception.savefailed;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class SavedFailedAdvice extends RuntimeException{
    @ResponseBody
    @ExceptionHandler(SavedFailed.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> exceptionHandler(SavedFailed exception){

        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        errorMap.put("status", String.valueOf(HttpStatus.NOT_FOUND));

        return errorMap;
    }

}
