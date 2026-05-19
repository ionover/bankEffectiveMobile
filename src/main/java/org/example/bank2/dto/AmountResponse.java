package org.example.bank2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.bank2.dto.enums.OperationStatus;

@Schema(description = "Результат операции с балансом карты")
public class AmountResponse {

    @Schema(description = "Сумма операции", example = "1000")
    private Long amount;

    @Schema(description = "Текущий баланс карты после операции", example = "6000")
    private Long currentBalance;

    @Schema(description = "Статус операции", example = "SUCCESS", allowableValues = {"SUCCESS", "FAILURE"})
    private OperationStatus status;

    @Schema(description = "Сообщение с результатом операции", example = "Операция выполнена успешно")
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
