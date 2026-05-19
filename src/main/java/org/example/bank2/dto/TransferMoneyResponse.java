package org.example.bank2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.bank2.dto.enums.OperationStatus;

@Schema(description = "Результат перевода денег между картами")
public class TransferMoneyResponse {

    @Schema(description = "Баланс карты списания после перевода", example = "4000")
    private Long cardFromBalance;

    @Schema(description = "Баланс карты зачисления после перевода", example = "6000")
    private Long cardWhereBalance;

    @Schema(description = "Статус операции", example = "SUCCESS", allowableValues = {"SUCCESS", "FAILURE"})
    private OperationStatus operationStatus;

    @Schema(description = "Сообщение с результатом операции", example = "Перевод выполнен успешно")
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
