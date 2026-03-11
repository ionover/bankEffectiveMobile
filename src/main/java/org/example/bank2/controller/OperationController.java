package org.example.bank2.controller;

import org.example.bank2.dto.AmountResponse;
import org.example.bank2.dto.TransferMoneyRequest;
import org.example.bank2.dto.TransferMoneyResponse;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.OperationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
public class OperationController {

    private final OperationsService operationsService;

    public OperationController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @PostMapping("/topOnBalance/{cardId}")
    public ResponseEntity<AmountResponse> addMoney(@PathVariable Long cardId,
                                                   @RequestBody Long depositAmount) {
        if (depositAmount == null || depositAmount <= 0) {
            throw new BadRequestException("Сумма пополнения должна быть больше нуля!!!");
        }

        AmountResponse response = operationsService.addMoneyOnCard(cardId, depositAmount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<TransferMoneyResponse> transferMoney(@RequestBody TransferMoneyRequest transferMoneyRequest) {
        if (transferMoneyRequest.getMoney() <= 0) {
            throw new BadRequestException("Сумма перевода должна быть больше нуля!!!");
        }

        TransferMoneyResponse response = operationsService.transferMoney(transferMoneyRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/frieze/{cardId}")
    public ResponseEntity<Object> frieze(@PathVariable Long cardId) {
        operationsService.friezeCard(cardId);

        return ResponseEntity.notFound().build();
    }
}
