package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.config.UserPrincipal;
import com.JSlog.JSblog.request.Signup;
import com.JSlog.JSblog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String login() {
        return "메인 페이지입니다.";
    }

    // SecurityConfig > @EnableMethodSecurity(prePostEnabled = true) 참고
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return "사용자 페이지입니다.";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "관리자 페이지입니다.";
    }
}
