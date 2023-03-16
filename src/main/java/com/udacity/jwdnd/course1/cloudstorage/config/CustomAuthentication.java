package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class CustomAuthentication implements AuthenticationProvider {
    private final UserService userService;

    public CustomAuthentication(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String auth = userService.authenticateUser(username, password);
        log.debug(username + " - " + auth);
        if (auth.equals("AUTH_VALID")) {
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        }
        if (auth.equals("ERROR_USER")) throw new UsernameNotFoundException("Username not found");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
