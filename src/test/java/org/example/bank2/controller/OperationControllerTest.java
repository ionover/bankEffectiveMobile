package org.example.bank2.controller;

import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.OperationsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class OperationControllerTest {

    @Test
    void addMoneyThrowsWhenAmountIsNotPositive() {
        OperationController controller = new OperationController(mock(OperationsService.class));

        assertThrows(BadRequestException.class, () -> controller.addMoney(1L, 0L));
    }
}
