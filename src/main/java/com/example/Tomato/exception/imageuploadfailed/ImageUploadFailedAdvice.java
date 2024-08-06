package com.example.Tomato.exception.imageuploadfailed;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class ImageUploadFailedAdvice {

    @ResponseBody
    @ExceptionHandler(ImageUploadFailed.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> exceptionHandler(ImageUploadFailed exception){

        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());
        errorMap.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));

        return errorMap;
    }
}
