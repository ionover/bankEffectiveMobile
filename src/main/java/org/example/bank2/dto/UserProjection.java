package org.example.bank2.dto;

public class UserProjection {

    private Long id;

    private String login;

    private String surname;

    private Boolean isAdmin;

    private String middleName;

    private String phone;

    private String name;

    public UserProjection() {
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
