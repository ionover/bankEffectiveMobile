package org.example.bank2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.bank2.dto.enums.CardStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner", nullable = false)
    @JsonBackReference
    private User owner;

    @Column(name = "validity_period")
    private LocalDateTime validityPeriod;

    @Enumerated(value = EnumType.STRING)
    private CardStatus status;

    @Column
    private Long balance;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    public Card() {
    }

    public Card(String number, User owner) {
        this.number = number;
        this.owner = owner;
    }

    public Card(Long id, String number, User owner, LocalDateTime validityPeriod, CardStatus status, Long balance,
                LocalDateTime createdAt, LocalDateTime updatedAt, Long version) {
        this.id = id;
        this.number = number;
        this.owner = owner;
        this.validityPeriod = validityPeriod;
        this.status = status;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + (id == null ? "null" : "\"" + id + "\"") + ", " +
                "\"number\":" + (number == null ? "null" : "\"" + number + "\"") + ", " +
                "\"owner\":" + (owner == null ? "null" : owner) + ", " +
                "\"validityPeriod\":" + (validityPeriod == null ? "null" : validityPeriod) + ", " +
                "\"status\":" + (status == null ? "null" : status) + ", " +
                "\"balance\":" + (balance == null ? "null" : "\"" + balance + "\"") +
                "}";
    }
}
