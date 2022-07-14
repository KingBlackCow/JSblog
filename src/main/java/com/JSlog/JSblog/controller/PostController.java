package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.domain.Post;
import com.JSlog.JSblog.request.PostCreate;
import com.JSlog.JSblog.response.PostResponse;
import com.JSlog.JSblog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequiredArgsConstructor
public class PostController {
    // SSR -> jsp, thymeleaf, mustache, freemarker
    // SPA ->
    //  vue + SSR = nuxt.js
    // -> javascript + <-> api (JSON)
    //  react + SSR = next.js

    //HTTP Method
    //GET, POST, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long Id){
        PostResponse response = postService.get(Id);
        return response;
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@PageableDefault(size = 5) Pageable pageable){
        return postService.getList(pageable);
    }

}
