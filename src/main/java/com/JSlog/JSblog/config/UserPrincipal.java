package com.JSlog.JSblog.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    // role: 역할 -> 관리자, 사용자, 매니저
    // authority: 권한 -> 글쓰기, 읽기

    public UserPrincipal(com.JSlog.JSblog.domain.User user) {
        // SimpleGrantedAuthority는 ROLE을 붙이면 역할 안붙이면 권한
        // ADMIN: 권한
        // ROLE_ADMIN: 역할
        super(
                user.getEmail(),
                user.getPassword(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN")//,
                        //new SimpleGrantedAuthority("WRITE")
                )
        );
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
