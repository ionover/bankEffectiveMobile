package org.example.bank2.entity;

import jakarta.persistence.*;
import org.example.bank2.dto.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    private Long id;

    @Column
    private String number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name = "validity_period")
    private LocalDateTime validityPeriod;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column
    private Long balance;

    public Card() {
    }

    public Card(Long id, String number, User owner, LocalDateTime validityPeriod, Status status, Long balance) {
        this.id = id;
        this.number = number;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDateTime getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(LocalDateTime validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
