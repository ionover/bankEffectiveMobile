package org.example.bank2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.bank2.dto.AmountResponse;
import org.example.bank2.dto.TransferMoneyRequest;
import org.example.bank2.dto.TransferMoneyResponse;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.OperationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
@Tag(name = "Operations", description = "Операции с балансом и картами")
public class OperationController {

    private final OperationsService operationsService;

    public OperationController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @PostMapping("/topOnBalance/{cardId}")
    @Operation(summary = "Пополнить баланс карты", description = "Пополняет баланс карты на указанную сумму")
    public ResponseEntity<AmountResponse> addMoney(@Parameter(description = "ID карты", example = "1")
                                                   @PathVariable Long cardId,
                                                   @RequestBody
                                                   @Schema(description = "Сумма пополнения", example = "1000")
                                                   Long depositAmount) {
        if (depositAmount == null || depositAmount <= 0) {
            throw new BadRequestException("Сумма пополнения должна быть больше нуля!!!");
        }

        AmountResponse response = operationsService.addMoneyOnCard(cardId, depositAmount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/transferMoney")
    @Operation(summary = "Перевести деньги", description = "Переводит деньги с одной карты на другую")
    public ResponseEntity<TransferMoneyResponse> transferMoney(@RequestBody TransferMoneyRequest transferMoneyRequest) {
        if (transferMoneyRequest.getMoney() <= 0) {
            throw new BadRequestException("Сумма перевода должна быть больше нуля!!!");
        }

        TransferMoneyResponse response = operationsService.transferMoney(transferMoneyRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/frieze/{cardId}")
    @Operation(summary = "Заморозить карту", description = "Переводит карту в статус заморозки")
    public ResponseEntity<Object> frieze(@Parameter(description = "ID карты", example = "1")
                                         @PathVariable Long cardId) {
        operationsService.friezeCard(cardId);

        return ResponseEntity.noContent().build();
    }
}
