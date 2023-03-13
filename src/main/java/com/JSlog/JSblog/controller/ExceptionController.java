package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.exception.InvalidRequest;
import com.JSlog.JSblog.exception.JslogException;
import com.JSlog.JSblog.exception.PostNotFound;
import com.JSlog.JSblog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
//            FieldError fieldError = e.getFieldError();
//            String field = fieldError.getField();
//            String message = fieldError.getDefaultMessage();
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .validation(new HashMap<>())
                .build();
        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JslogException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> postsNotFound(JslogException e) {
        int statusCode = e.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode).body(body);
        return response;
    }
}
