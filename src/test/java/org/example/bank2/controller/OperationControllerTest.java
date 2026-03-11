package org.example.bank2.controller;

import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.OperationsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationControllerTest {

    @Test
    void addMoneyThrowsWhenAmountIsNotPositive() {
        OperationController controller = new OperationController(new OperationsService(null));

        assertThrows(BadRequestException.class, () -> controller.addMoney(1L, 0L));
    }
}
