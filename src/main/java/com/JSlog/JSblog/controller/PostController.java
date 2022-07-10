package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String post(@RequestBody PostCreate params) {
        // @RequestParam String title, @RequestParam String content
        //  Map<String, String> params 이렇게 받아도되고
        log.info("params={}", params.toString());
        return "Hello World";
    }
}
