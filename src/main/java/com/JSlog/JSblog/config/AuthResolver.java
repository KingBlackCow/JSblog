package com.JSlog.JSblog.config;

import com.JSlog.JSblog.config.data.UserSession;
import com.JSlog.JSblog.domain.Session;
import com.JSlog.JSblog.exception.UnAuthorized;
import com.JSlog.JSblog.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

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

        String accessToken = webRequest.getHeader("Authorization");
        if (accessToken == null || accessToken.equals("")) {
            throw new UnAuthorized();
        }

        Session sessionOption = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(UnAuthorized::new);


        return new UserSession(sessionOption.getUser().getId());
    }
}
