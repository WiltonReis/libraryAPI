package io.github.wiltonreis.library.security;

import io.github.wiltonreis.library.model.User;
import io.github.wiltonreis.library.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findByLogin(login);

        if (user == null) throw getNotFoundUser();

        String encryptedPassword = user.getPassword();
        boolean passwordMatch = passwordEncoder.matches(password, encryptedPassword);

        if (passwordMatch) return new CustomAuthentication(user);

        throw getNotFoundUser();
    }

    private UsernameNotFoundException getNotFoundUser() {
        return new UsernameNotFoundException("Invalid credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
