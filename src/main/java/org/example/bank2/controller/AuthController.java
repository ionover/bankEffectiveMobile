package org.example.bank2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.bank2.dto.LoginDto;
import org.example.bank2.dto.UserProjection;
import org.example.bank2.exception.UnauthorizedException;
import org.example.bank2.service.UserService;
import org.example.bank2.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@Tag(name = "Authentication", description = "Аутентификация пользователей")
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
    @Operation(summary = "Получить JWT токен",
               description = "Проверяет логин и пароль пользователя и возвращает JWT токен",
               security = {})
    public ResponseEntity<String> getToken(@RequestBody @Valid LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getLogin(),
                            loginDto.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Неверный логин или пароль");
        }

        UserProjection user = userService.getUserProjectionByLogin(loginDto.getLogin());

        String token = jwtUtil.generateToken(user.getLogin(), user.getIsAdmin());

        return ResponseEntity.ok(token);
    }
}
