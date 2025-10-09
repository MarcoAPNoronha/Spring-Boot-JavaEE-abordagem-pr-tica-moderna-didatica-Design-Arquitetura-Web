package com.marconoronha.spring_boot_java_ee_arquit_web.exception;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class PersonSaveOrUpdateByIdFailedException extends RuntimeException {

    private String message;

    public PersonSaveOrUpdateByIdFailedException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
