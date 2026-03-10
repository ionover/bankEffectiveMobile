package org.example.bank2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.bank2.controller.OnCreate;

public class UserRequest {

    @NotBlank(groups = OnCreate.class)
    private String login;

    @NotBlank(groups = OnCreate.class)
    private String password;

    @NotBlank(groups = OnCreate.class)
    private String surname;

    @NotNull(groups = OnCreate.class)
    private Boolean isAdmin;

    private String name;
    private String middleName;
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
