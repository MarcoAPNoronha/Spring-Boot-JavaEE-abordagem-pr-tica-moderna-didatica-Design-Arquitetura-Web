package com.marconoronha.spring_boot_java_ee_arquit_web.exception;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;


@Log
@ControllerAdvice //Controla todos os handler de todos os controllers para lançar exceções
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonDeleteByIdFailedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    ErrorResponse personDeleteByIdFailedException(PersonDeleteByIdFailedException e){
        log.log(Level.WARNING, e::toString);
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.name(), e.getMessage());
    }


    @ExceptionHandler(PersonSaveOrUpdateByIdFailedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    ErrorResponse personSaveOrUpdateByIdFailedException(PersonSaveOrUpdateByIdFailedException e){
        log.log(Level.WARNING, e::toString);
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.name(), e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.log(Level.WARNING, e::toString);
        String errorDetailMessage = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).reduce((a, b)->a+". "+b).orElseGet(()->"Unkown errors")+".";
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                "Bad request: " +e.getClass().getSimpleName() + "! Errors found at the content of your request body. " + errorDetailMessage);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.log(Level.WARNING, e::toString);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                "Bad request: " + e.getClass().getSimpleName() + "! " + e.getMessage() + ". " +
                        "Request body content incompatible with field 'acept' at Http request header, "+
                        "Media (MIME) type. Cannot unmarshall request body content.");
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleGeneralException(Exception e){
        log.log(Level.WARNING, e::toString);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                 "Internal server error: " + e.getClass().getSimpleName() + "!");
    }


}
