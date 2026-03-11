package org.example.bank2.service;

import org.example.bank2.dto.AmountResponse;
import org.example.bank2.dto.TransferMoneyRequest;
import org.example.bank2.dto.TransferMoneyResponse;
import org.example.bank2.entity.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.example.bank2.dto.enums.CardStatus.ACTIVE;
import static org.example.bank2.dto.enums.CardStatus.FRIEZE;
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
        card = cardService.updateCard(card);
        response.setCurrentBalance(card.getBalance());
        response.setStatus(SUCCESS);

        return response;
    }

    @Transactional
    public TransferMoneyResponse transferMoney(TransferMoneyRequest request) {
        TransferMoneyResponse response = new TransferMoneyResponse(request.getCardFromId(),
                                                                   request.getCardWhereId());

        Card cardFrom = cardService.getAccessibleCardById(request.getCardFromId());
        Card cardWhere = cardService.getAccessibleCardById(request.getCardWhereId());

        if (isCardsInvalid(response, cardFrom, cardWhere)) {
            return response;
        }

        if (cardFrom.getBalance() < request.getMoney()) {
            response.setCardFromBalance(cardFrom.getBalance());
            response.setCardWhereBalance(cardWhere.getBalance());
            response.setOperationStatus(FAILURE);
            response.setMessage("Недостаточно средств для перевода");

            return response;
        }

        cardFrom.setBalance(cardFrom.getBalance() - request.getMoney());
        cardWhere.setBalance(cardWhere.getBalance() + request.getMoney());

        cardService.updateCard(cardFrom);
        cardService.updateCard(cardWhere);

        response.setCardFromBalance(cardFrom.getBalance());
        response.setCardWhereBalance(cardWhere.getBalance());
        response.setOperationStatus(SUCCESS);
        response.setMessage("Перевод выполнен успешно");

        return response;
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

    public void friezeCard(Long cardId) {
        Card card = cardService.getAccessibleCardById(cardId);
        log.debug("Замораживаем карту {}", card);

        card.setStatus(FRIEZE);
        cardService.updateCard(card);
    }

    private boolean isCardsInvalid(TransferMoneyResponse response, Card cardFrom, Card cardWhere) {
        response.setCardFromBalance(cardFrom.getBalance());
        response.setCardWhereBalance(cardWhere.getBalance());

        if (cardFrom.getStatus() != ACTIVE && cardWhere.getStatus() != ACTIVE) {
            response.setOperationStatus(FAILURE);
            response.setMessage("Обе карты неактивны, перевод невозможен");

            return true;
        }

        if (cardFrom.getStatus() != ACTIVE) {
            response.setOperationStatus(FAILURE);
            response.setMessage("Карта списания неактивна, перевод невозможен");

            return true;
        }

        if (cardWhere.getStatus() != ACTIVE) {
            response.setOperationStatus(FAILURE);
            response.setMessage("Карта получения неактивна, перевод невозможен");

            return true;
        }

        boolean fromExpired = LocalDateTime.now().isAfter(cardFrom.getValidityPeriod());
        boolean whereExpired = LocalDateTime.now().isAfter(cardWhere.getValidityPeriod());

        if (fromExpired && whereExpired) {
            response.setOperationStatus(FAILURE);
            response.setMessage("Срок действия обеих карт истёк, перевод невозможен");

            return true;
        }

        if (fromExpired) {
            response.setOperationStatus(FAILURE);
            response.setMessage("Срок действия карты списания истёк, перевод невозможен");

            return true;
        }

        if (whereExpired) {
            response.setOperationStatus(FAILURE);
            response.setMessage("Срок действия карты получения истёк, перевод невозможен");

            return true;
        }

        return false;
    }
}
