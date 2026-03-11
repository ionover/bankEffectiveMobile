package org.example.bank2.service;

import org.example.bank2.dto.AmountResponse;
import org.example.bank2.dto.TransferMoneyResponse;
import org.example.bank2.entity.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.example.bank2.dto.enums.CardStatus.ACTIVE;
import static org.example.bank2.dto.enums.OperationStatus.FAILURE;
import static org.example.bank2.dto.enums.OperationStatus.SUCCESS;

@Service
public class OperationsService {

    private final Logger log = LoggerFactory.getLogger(OperationsService.class);

    private final CardService cardService;

    public OperationsService(CardService cardService) {
        this.cardService = cardService;
    }

    public AmountResponse addMoneyOnCard(Long cardId, Long depositAmount) {
        AmountResponse response = new AmountResponse(depositAmount);

        Card card = cardService.getAccessibleCardById(cardId);

        if (isCardInvalid(response, card)) {
            return response;
        }

        card.setBalance(card.getBalance() + depositAmount);
        card = cardService.topOnBalance(card);
        response.setCurrentBalance(card.getBalance());
        response.setStatus(SUCCESS);

        return response;
    }

    public TransferMoneyResponse transferMoney() {
        return null;
    }

    private boolean isCardInvalid(AmountResponse response, Card card) {
        if (card.getStatus() != ACTIVE) {
            String msg = "Невозможно пополнить баланс неактивной карты !!!";
            response.setMessage(msg);
            response.setCurrentBalance(card.getBalance());
            response.setStatus(FAILURE);

            return true;
        }

        if (LocalDateTime.now().isAfter(card.getValidityPeriod())) {
            String msg = "Срок действия карты истёк, все операции с картой недоступны!!!";
            response.setMessage(msg);
            response.setCurrentBalance(card.getBalance());
            response.setStatus(FAILURE);

            return true;
        }

        return false;
    }
}
