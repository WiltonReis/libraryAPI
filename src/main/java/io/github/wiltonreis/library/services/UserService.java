package io.github.wiltonreis.library.services;

import io.github.wiltonreis.library.model.User;
import io.github.wiltonreis.library.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }
}
