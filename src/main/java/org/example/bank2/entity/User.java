package org.example.bank2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String login;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String name;

    @Column
    private String surname;

    @Column
    private String middleName;

    @Column
    private String phone;

    @Column
    private Boolean isAdmin;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards;

    public User() {
    }

    public User(Long id, String login, String password, String name, String surname, String middleName, String phone,
                Boolean isAdmin, Set<Card> cards) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.phone = phone;
        this.isAdmin = isAdmin;
        this.cards = cards;
    }

    public User(String login, String password, String name, String surname, String middleName, String phone) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.phone = phone;
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

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
