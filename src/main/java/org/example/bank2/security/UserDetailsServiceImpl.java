package org.example.bank2.security;

import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(username);

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                user.isAdmin() ? "ADMIN" : "USER"
        );

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
