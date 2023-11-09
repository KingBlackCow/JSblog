package com.JSlog.JSblog.service;

import com.JSlog.JSblog.crypto.PasswordEncoder;
import com.JSlog.JSblog.domain.User;
import com.JSlog.JSblog.exception.AlreadyExistsEmailException;
import com.JSlog.JSblog.repository.UserRepository;
import com.JSlog.JSblog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

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
