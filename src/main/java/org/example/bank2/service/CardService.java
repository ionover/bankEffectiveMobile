package org.example.bank2.service;

import org.example.bank2.dto.CardResponse;
import org.example.bank2.dto.enums.CardStatus;
import org.example.bank2.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {

    Page<CardResponse> getAllCards(Pageable pageable, String number, CardStatus status, Long balance);

    CardResponse getCardById(Long id);

    CardResponse createCard(Card card);

    void deleteById(Long id);

    void updateCardStatus(Long id, CardStatus status);
}
