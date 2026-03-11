package org.example.bank2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.bank2.entity.User;

public class CardRequest {

    @NotNull
    @Size(min = 4, max = 20)
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
