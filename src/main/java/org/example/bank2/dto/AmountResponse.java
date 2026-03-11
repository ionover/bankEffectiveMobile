package org.example.bank2.dto;

import org.example.bank2.dto.enums.OperationStatus;

public class AmountResponse {

    private Long amount;
    private Long currentBalance;
    private OperationStatus status;
    private String message;

    public AmountResponse() {
    }

    public AmountResponse(Long amount) {
        this.amount = amount;
    }

    public AmountResponse(Long amount, Long currentBalance, OperationStatus status, String message) {
        this.amount = amount;
        this.currentBalance = currentBalance;
        this.status = status;
        this.message = message;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
