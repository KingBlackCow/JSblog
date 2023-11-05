package com.JSlog.JSblog.service;

import com.JSlog.JSblog.domain.User;
import com.JSlog.JSblog.exception.AlreadyExistsEmailException;
import com.JSlog.JSblog.repository.UserRepository;
import com.JSlog.JSblog.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        Signup signup = Signup.builder()
                .email("sgs1159@gmail.com")
                .password("1234")
                .name("이조순")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("sgs1159@gmail.com", user.getEmail());
        assertNotNull(user.getPassword());
//        assertNotEquals("1234", user.getPassword());
        assertEquals("이조순", user.getName());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2() {
        // given
        User user = User.builder()
                .email("sgs1159@gmail.com")
                .password("1234")
                .name("이조순1")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .email("sgs1159@gmail.com")
                .password("1234")
                .name("이조순2")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }
}