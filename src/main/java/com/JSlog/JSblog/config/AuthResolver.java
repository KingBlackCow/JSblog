package com.JSlog.JSblog.config;

import com.JSlog.JSblog.config.data.UserSession;
import com.JSlog.JSblog.domain.Session;
import com.JSlog.JSblog.exception.UnAuthorized;
import com.JSlog.JSblog.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info(">>>{}", appConfig.toString());

        // Authorization
        String jws = webRequest.getHeader("Authorization");
        if (jws == null || jws.equals("")) {
            throw new UnAuthorized();
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(appConfig.getJwtKey())
                    .build()
                    .parseClaimsJws(jws);
            String userId = claims.getBody().getSubject();
            return new UserSession(Long.parseLong(userId));
        } catch (JwtException e) {
            throw new UnAuthorized();
        }

        // Cookie
//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//        if (servletRequest == null) {
//            log.error("serveletRequest is null");
//            throw new UnAuthorized();
//        }
//
//        Cookie[] cookies = servletRequest.getCookies();
//        if (cookies.length == 0) {
//            log.error("쿠키가 없음");
//            throw new UnAuthorized();
//        }
//
//        String accessToken = cookies[0].getValue();
//
//        Session sessionOption = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(UnAuthorized::new);

//        return new UserSession(sessionOption.getUser().getId());
    }
}
