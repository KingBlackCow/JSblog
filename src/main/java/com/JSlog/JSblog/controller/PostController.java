package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {
    // SSR -> jsp, thymeleaf, mustache, freemarker
    // SPA ->
    //  vue + SSR = nuxt.js
    // -> javascript + <-> api (JSON)
    //  react + SSR = next.js

    //HTTP Method
    //GET, POST, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) {
        // @RequestParam String title, @RequestParam String content
        //  Map<String, String> params 이렇게 받아도되고
        // 데이터를 검증하는 이유
        // 1. client 개발자가 깜박할 수 있다. 실수로 값을 안보낼 수 있다.
        // 2. client bug로 값이 누락될 수 있다.
        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField(); //title
            String errorMessage = firstFieldError.getDefaultMessage(); //..에러메시지

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        log.info("params={}", params.toString());

        return Map.of();
    }
}
