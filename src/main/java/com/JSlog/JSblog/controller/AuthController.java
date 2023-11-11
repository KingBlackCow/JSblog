package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.request.Signup;
import com.JSlog.JSblog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "로그인 페이지입니다.";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }
}
