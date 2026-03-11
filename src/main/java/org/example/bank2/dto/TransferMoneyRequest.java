package org.example.bank2.dto;

import jakarta.validation.constraints.NotNull;
import org.example.bank2.dto.enums.OperationStatus;

public class TransferMoneyRequest {

    @NotNull
    private Long cardFromId;
    @NotNull
    private Long cardWhereId;
    @NotNull
    private Long money;

    public TransferMoneyRequest() {
    }

    public Long getCardFromId() {
        return cardFromId;
    }

    public void setCardFromId(Long cardFromId) {
        this.cardFromId = cardFromId;
    }

    public Long getCardWhereId() {
        return cardWhereId;
    }

    public void setCardWhereId(Long cardWhereId) {
        this.cardWhereId = cardWhereId;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}

