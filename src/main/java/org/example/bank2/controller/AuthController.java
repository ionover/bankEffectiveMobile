package org.example.bank2.controller;

import jakarta.validation.Valid;
import org.example.bank2.dto.LoginDto;
import org.example.bank2.entity.User;
import org.example.bank2.service.UserService;
import org.example.bank2.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody @Valid LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getLogin(),
                        loginDto.getPassword()
                )
        );

        User user = userService.getUserByLogin(loginDto.getLogin());

        String token = jwtUtil.generateToken(user.getLogin(), user.isAdmin());

        return ResponseEntity.ok(token);
    }
}
