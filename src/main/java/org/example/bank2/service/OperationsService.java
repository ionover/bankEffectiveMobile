package org.example.bank2.service;

import org.example.bank2.dto.AmountResponse;
import org.example.bank2.dto.TransferMoneyRequest;
import org.example.bank2.dto.TransferMoneyResponse;

public interface OperationsService {

    AmountResponse addMoneyOnCard(Long cardId, Long depositAmount);

    TransferMoneyResponse transferMoney(TransferMoneyRequest request);

    void friezeCard(Long cardId);
}
