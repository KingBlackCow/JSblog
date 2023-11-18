package com.JSlog.JSblog.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = JsMockSecurityContext.class)
public @interface JsMockUser {

    String name() default "js";

    String email() default "sgs1159@gmail.com";

    String password() default "1234";

//    String role() default "ROLE_ADMIN";
}
