package com.JSlog.JSblog.service;

import com.JSlog.JSblog.domain.Session;
import com.JSlog.JSblog.domain.User;
import com.JSlog.JSblog.exception.AlreadyExistsEmailException;
import com.JSlog.JSblog.exception.InvalidSigninInformation;
import com.JSlog.JSblog.repository.UserRepository;
import com.JSlog.JSblog.request.Login;
import com.JSlog.JSblog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);
//        Session session = user.addSession();
//        return session.getAccessToken();
        return user.getId();
    }

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        var user = User.builder()
                .email(signup.getEmail())
                .password("1234")
                .name(signup.getName())
                .build();
        userRepository.save(user);
    }
}
