package org.example.bank2.controller;

import org.example.bank2.dto.CardResponse;
import org.example.bank2.service.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardControllerTest {

    @Test
    void getCardReturnsCardFromService() {
        CardResponse expected = new CardResponse();
        expected.setId(1L);
        CardServiceImpl cardService = new CardServiceImpl(null, null) {
            @Override
            public CardResponse getCardById(Long id) {
                return expected;
            }
        };
        CardController controller = new CardController(cardService);

        ResponseEntity<CardResponse> response = controller.getCard(1L);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expected, response.getBody());
    }
}
