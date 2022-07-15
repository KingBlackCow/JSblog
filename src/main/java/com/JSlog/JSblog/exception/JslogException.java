package com.JSlog.JSblog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class JslogException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public JslogException(String message) {
        super(message);
    }

    public JslogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int statusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }
}
