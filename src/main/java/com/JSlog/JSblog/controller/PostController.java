package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.request.PostCreate;
import com.JSlog.JSblog.request.PostEdit;
import com.JSlog.JSblog.request.PostSearch;
import com.JSlog.JSblog.response.PostResponse;
import com.JSlog.JSblog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        request.validate();
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long Id) {
        PostResponse response = postService.get(Id);
        return response;
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId) {
        postService.delete(postId);
    }

}
