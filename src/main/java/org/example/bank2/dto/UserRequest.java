package org.example.bank2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.bank2.controller.OnCreate;

@Schema(description = "Запрос на создание или обновление пользователя")
public class UserRequest {

    @Schema(description = "Логин пользователя. Обязателен при создании и не изменяется при обновлении",
            example = "user@mail.com")
    @NotBlank(groups = OnCreate.class)
    private String login;

    @Schema(description = "Пароль пользователя. Обязателен при создании", example = "password123")
    @NotBlank(groups = OnCreate.class)
    private String password;

    @Schema(description = "Фамилия пользователя. Обязательна при создании", example = "Иванов")
    @NotBlank(groups = OnCreate.class)
    private String surname;

    @Schema(description = "Признак администратора. Обязателен при создании", example = "false")
    @NotNull(groups = OnCreate.class)
    private Boolean isAdmin;

    @Schema(description = "Имя пользователя", example = "Иван")
    private String name;

    @Schema(description = "Отчество пользователя", example = "Иванович")
    private String middleName;

    @Schema(description = "Телефон пользователя", example = "+79991234567")
    private String phone;

    public UserRequest() {
    }

    public UserRequest(String login, String password, String name, String surname, String middleName, String phone,
                       Boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.phone = phone;
        this.isAdmin = isAdmin;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
