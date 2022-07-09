package com.kolya.chatapp.security;

import com.kolya.chatapp.model.CustomUserDetails;
import com.kolya.chatapp.model.User;
import com.kolya.chatapp.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kolya.chatapp.util.SecurityUtil.USER_PASSWORD;

@Component
public class AuthProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    @Autowired
    public AuthProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String userName = authentication.getName();

            Optional<User> ifUser = userRepository.findUserByName(userName);
            User user = ifUser.isEmpty() ?
                    User.builder()
                    .name(userName)
                            .build() : ifUser.get();
            userRepository.save(user);

            UserDetails principal = new CustomUserDetails(user);
            return new UsernamePasswordAuthenticationToken(
                    principal, USER_PASSWORD() , principal.getAuthorities()
            );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
