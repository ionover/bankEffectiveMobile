package org.example.bank2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание банковской карты")
public class CardRequest {

    @Schema(description = "Номер карты", example = "1234567890123456", minLength = 4, maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Size(min = 4, max = 20)
    @Pattern(regexp = "[0-9\\s-]+", message = "должен содержать только цифры, пробелы или дефисы")
    private String number;

    @Schema(description = "ID владельца карты", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long owner;

    public CardRequest() {
    }

    public CardRequest(String number, Long owner) {
        this.number = number;
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
