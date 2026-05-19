package org.example.bank2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.bank2.dto.enums.CardStatus;

import java.time.LocalDateTime;

@Schema(description = "Информация о банковской карте")
public class CardResponse {

    @Schema(description = "ID карты", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Номер карты", example = "1234567890123456")
    private String number;

    @Schema(description = "ID владельца карты", example = "1")
    private Long ownerId;

    @Schema(description = "Срок действия карты", example = "2027-05-19T12:00:00")
    private LocalDateTime validityPeriod;

    @Schema(description = "Статус карты", example = "ACTIVE", allowableValues = {"ACTIVE", "BLOCKED", "FRIEZE"})
    private CardStatus status;

    @Schema(description = "Текущий баланс карты", example = "5000")
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
