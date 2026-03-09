package org.example.bank2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserRequest {

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String middleName;

    @NotNull
    private String phone;

    public UserRequest() {
    }

    public UserRequest(String login, String password, String name, String surname, String middleName, String phone) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.phone = phone;
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
}
