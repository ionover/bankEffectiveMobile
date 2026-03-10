package org.example.bank2.dto;

import jakarta.validation.constraints.NotNull;
import org.example.bank2.entity.User;

public class CardRequest {

    @NotNull
    private String number;

    @NotNull
    private Long owner;

    public CardRequest() {
    }

    public CardRequest(String number, Long owner) {
        this.number = number;
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
