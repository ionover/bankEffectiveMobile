package org.example.bank2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация о пользователе")
public class UserProjection {

    @Schema(description = "ID пользователя", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Логин пользователя", example = "user@mail.com")
    private String login;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String surname;

    @Schema(description = "Признак администратора", example = "false")
    private Boolean isAdmin;

    @Schema(description = "Отчество пользователя", example = "Иванович")
    private String middleName;

    @Schema(description = "Телефон пользователя", example = "+79991234567")
    private String phone;

    @Schema(description = "Имя пользователя", example = "Иван")
    private String name;

    public UserProjection() {
    }

    public UserProjection(Long id) {
        this.id = id;
    }

    public UserProjection(Long id, String login, String surname, Boolean isAdmin, String middleName,
                          String phone, String name) {
        this.id = id;
        this.login = login;
        this.surname = surname;
        this.isAdmin = isAdmin;
        this.middleName = middleName;
        this.phone = phone;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
