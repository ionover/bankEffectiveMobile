package org.example.bank2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на получение JWT токена")
public class LoginDto {

    @Schema(description = "Логин пользователя", example = "systemAdmin@mail.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @Schema(description = "Пароль пользователя", example = "password123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    public LoginDto() {
    }

    public LoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
