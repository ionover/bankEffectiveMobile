package org.example.bank2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на перевод денег между картами")
public class TransferMoneyRequest {

    @Schema(description = "ID карты списания", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long cardFromId;

    @Schema(description = "ID карты зачисления", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long cardWhereId;

    @Schema(description = "Сумма перевода", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED)
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
