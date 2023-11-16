package com.JSlog.JSblog.config;

import com.JSlog.JSblog.config.handler.Http401Handler;
import com.JSlog.JSblog.config.handler.Http403Handler;
import com.JSlog.JSblog.config.handler.LoginFailHandler;
import com.JSlog.JSblog.domain.User;
import com.JSlog.JSblog.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import java.io.IOException;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error")
                .requestMatchers(toH2Console());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers( "/auth/login").permitAll()
                .requestMatchers("/auth/signup").permitAll()
                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 역할이 있는지
//                .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))// 관리자 역할도 있고 쓰기 권한이 있는지
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/")
                .failureHandler(new LoginFailHandler(objectMapper))
                .and()
                .exceptionHandling(e ->  {
                    e.accessDeniedHandler(new Http403Handler(objectMapper));
                    e.authenticationEntryPoint(new Http401Handler(objectMapper));
                })
                .rememberMe(rm -> rm.rememberMeParameter("remember")
                        .alwaysRemember(false)
                        .tokenValiditySeconds(2592000)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
            return new UserPrincipal(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(
                16,
                8,
                1,
                32,
                64);
    }
}
