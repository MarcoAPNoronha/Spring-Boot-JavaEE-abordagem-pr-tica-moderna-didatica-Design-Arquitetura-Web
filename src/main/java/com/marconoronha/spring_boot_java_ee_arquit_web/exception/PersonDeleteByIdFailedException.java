package com.marconoronha.spring_boot_java_ee_arquit_web.exception;

public class PersonDeleteByIdFailedException extends RuntimeException {

    private String message;

    public PersonDeleteByIdFailedException(String message) {
        super(message);
        this.message = message;
    }


    @Override
    public String getMessage(){
        return  message;
    }



}
