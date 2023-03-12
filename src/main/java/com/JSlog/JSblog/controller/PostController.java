package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.config.data.UserSession;
import com.JSlog.JSblog.request.PostCreate;
import com.JSlog.JSblog.request.PostEdit;
import com.JSlog.JSblog.request.PostSearch;
import com.JSlog.JSblog.response.PostResponse;
import com.JSlog.JSblog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    // HandlerInterceptor
    @GetMapping("/foo")
    public String foo(@RequestAttribute("userName") String userName) {
        log.info(">>>{}", userName);

        return "foo";
    }

    // HandlerMethodArgumentResolver
    @GetMapping("/foo2")
    public Long foo2(UserSession userSession) {
        log.info(">>>{}", userSession.id);

        return userSession.id;
    }

    // HandlerMethodArgumentResolver
    @GetMapping("/bar")
    public String bar() {
        System.out.println("UserSession이 AuthResolver(HandelerArgumentResolver)에 없으므로");
        return "인증이 필요없는 페이지";
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

//    @GetMapping("/posts")
//    public List<PostResponse> getList(@PageableDefault(size = 5) Pageable pageable){
//        return postService.getList(pageable);
//    }

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
