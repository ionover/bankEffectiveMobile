package org.example.bank2.dto;

import org.example.bank2.dto.enums.CardStatus;

import java.time.LocalDateTime;

public class CardResponse {

    private Long id;
    private String number;
    private Long ownerId;
    private LocalDateTime validityPeriod;
    private CardStatus status;
    private Long balance;

    public CardResponse() {
    }

    public CardResponse(Long id,
                        String number,
                        Long ownerId,
                        LocalDateTime validityPeriod,
                        CardStatus status,
                        Long balance) {
        this.id = id;
        this.number = number;
        this.ownerId = ownerId;
        this.validityPeriod = validityPeriod;
        this.status = status;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(LocalDateTime validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
