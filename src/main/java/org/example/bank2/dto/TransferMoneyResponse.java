package org.example.bank2.dto;

import org.example.bank2.dto.enums.OperationStatus;

public class TransferMoneyResponse {

    private Long cardFromBalance;
    private Long cardWhereBalance;

    private OperationStatus operationStatus;
    private String message;

    public TransferMoneyResponse() {
    }

    public TransferMoneyResponse(Long cardFromBalance, Long cardWhereBalance) {
        this.cardFromBalance = cardFromBalance;
        this.cardWhereBalance = cardWhereBalance;
    }

    public Long getCardFromBalance() {
        return cardFromBalance;
    }

    public void setCardFromBalance(Long cardFromBalance) {
        this.cardFromBalance = cardFromBalance;
    }

    public Long getCardWhereBalance() {
        return cardWhereBalance;
    }

    public void setCardWhereBalance(Long cardWhereBalance) {
        this.cardWhereBalance = cardWhereBalance;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

