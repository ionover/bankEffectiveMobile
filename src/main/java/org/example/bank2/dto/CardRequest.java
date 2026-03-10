package org.example.bank2.dto;

import jakarta.validation.constraints.NotNull;
import org.example.bank2.entity.User;

public class CardRequest {

    @NotNull
    private String number;

    @NotNull
    private User owner;

    public CardRequest() {
    }

    public CardRequest(String number, User owner) {
        this.number = number;
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
