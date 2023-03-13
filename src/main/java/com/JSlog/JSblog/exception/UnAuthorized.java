package com.JSlog.JSblog.exception;

import lombok.Getter;

@Getter
public class UnAuthorized extends JslogException{

    private static final String MESSAGE = "인증이 필요합니다.";

    public UnAuthorized() {
        super(MESSAGE);
    }

    public UnAuthorized(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode(){
        return 401;
    }
}
