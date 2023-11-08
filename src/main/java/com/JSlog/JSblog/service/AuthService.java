package com.JSlog.JSblog.service;

import com.JSlog.JSblog.crypto.PasswordEncoder;
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
    private final PasswordEncoder encoder;

    @Transactional
    public Long signIn(Login login) {
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        if (!encoder.matches(login.getPassword(), user.getPassword())) throw new InvalidSigninInformation();
        return user.getId();
    }

    @Transactional
    public String signInToken(Login login) {
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);
        Session session = user.addSession();
        return session.getAccessToken();
    }

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) throw new AlreadyExistsEmailException();

        String encryptedPassword = encoder.encrpyt(signup.getPassword());

        var user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();
        userRepository.save(user);
    }
}
