package org.example.bank2.controller;

import org.example.bank2.dto.CardResponse;
import org.example.bank2.dto.enums.CardStatus;
import org.example.bank2.entity.Card;
import org.example.bank2.entity.User;
import org.example.bank2.repository.CardRepository;
import org.example.bank2.service.CardService;
import org.example.bank2.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository repository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CardService cardService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAllCardsWithoutFiltersReturnsAllCardsWithPaginationForAdmin() {
        authenticate("admin", "ADMIN");
        Pageable pageable = PageRequest.of(0, 2);
        Page<Card> cardsPage = new PageImpl<>(List.of(
                card(1L, "1111222233334444", 10L, CardStatus.ACTIVE, 500L),
                card(2L, "5555666677778888", 11L, CardStatus.BLOCKED, 900L)
        ), pageable, 5);
        when(repository.findAllByFilters(null, null, null, pageable)).thenReturn(cardsPage);

        Page<CardResponse> result = cardService.getAllCards(pageable, null, null, null);

        assertEquals(2, result.getContent().size());
        assertEquals(5, result.getTotalElements());
        assertEquals("**** **** **** 4444", result.getContent().get(0).getNumber());
        assertEquals("**** **** **** 8888", result.getContent().get(1).getNumber());
        verify(repository).findAllByFilters(null, null, null, pageable);
    }

    @Test
    void getAllCardsWithNumberFilterReturnsDifferentCardsForAdmin() {
        authenticate("admin", "ADMIN");
        Pageable pageable = PageRequest.of(0, 3);
        String numberFilter = "9012";
        Page<Card> cardsPage = new PageImpl<>(List.of(
                card(3L, "1234901200000001", 12L, CardStatus.ACTIVE, 1200L)
        ), pageable, 1);
        when(repository.findAllByFilters(numberFilter, null, null, pageable)).thenReturn(cardsPage);

        Page<CardResponse> result = cardService.getAllCards(pageable, numberFilter, null, null);

        assertEquals(1, result.getContent().size());
        assertEquals(3L, result.getContent().get(0).getId());
        assertEquals("**** **** **** 0001", result.getContent().get(0).getNumber());
        verify(repository).findAllByFilters(numberFilter, null, null, pageable);
    }

    @Test
    void getAllCardsWithStatusFilterReturnsDifferentCardsForAdmin() {
        authenticate("admin", "ADMIN");
        Pageable pageable = PageRequest.of(1, 1);
        Page<Card> cardsPage = new PageImpl<>(List.of(
                card(4L, "4000000000000004", 13L, CardStatus.FRIEZE, 700L)
        ), pageable, 2);
        when(repository.findAllByFilters(null, CardStatus.FRIEZE, null, pageable)).thenReturn(cardsPage);

        Page<CardResponse> result = cardService.getAllCards(pageable, null, CardStatus.FRIEZE, null);

        assertEquals(1, result.getContent().size());
        assertEquals(CardStatus.FRIEZE, result.getContent().get(0).getStatus());
        assertEquals(2, result.getTotalElements());
        verify(repository).findAllByFilters(null, CardStatus.FRIEZE, null, pageable);
    }

    @Test
    void getAllCardsWithBalanceFilterReturnsDifferentCardsForAdmin() {
        authenticate("admin", "ADMIN");
        Pageable pageable = PageRequest.of(0, 5);
        Long balanceFilter = 15_000L;
        Page<Card> cardsPage = new PageImpl<>(List.of(
                card(5L, "5100000000000005", 14L, CardStatus.BLOCKED, 15_000L)
        ), pageable, 1);
        when(repository.findAllByFilters(null, null, balanceFilter, pageable)).thenReturn(cardsPage);

        Page<CardResponse> result = cardService.getAllCards(pageable, null, null, balanceFilter);

        assertEquals(1, result.getContent().size());
        assertEquals(15_000L, result.getContent().get(0).getBalance());
        assertEquals("**** **** **** 0005", result.getContent().get(0).getNumber());
        verify(repository).findAllByFilters(null, null, balanceFilter, pageable);
    }

    @Test
    void getAllCardsWithAllFiltersReturnsDifferentCardsForCurrentUser() {
        authenticate("user-login", "USER");
        Pageable pageable = PageRequest.of(0, 2);
        String numberFilter = "7777";
        CardStatus statusFilter = CardStatus.ACTIVE;
        Long balanceFilter = 2_500L;
        User currentUser = new User(77L);
        when(userService.getUserByLogin("user-login")).thenReturn(currentUser);
        Page<Card> cardsPage = new PageImpl<>(List.of(
                card(6L, "9999000077771234", 77L, CardStatus.ACTIVE, 2_500L)
        ), pageable, 1);
        when(repository.findAllByOwnerIdAndFilters(77L, numberFilter, statusFilter, balanceFilter, pageable))
                .thenReturn(cardsPage);

        Page<CardResponse> result = cardService.getAllCards(pageable, numberFilter, statusFilter, balanceFilter);

        assertEquals(1, result.getContent().size());
        assertEquals(77L, result.getContent().get(0).getOwnerId());
        assertEquals("**** **** **** 1234", result.getContent().get(0).getNumber());
        verify(userService).getUserByLogin("user-login");
        verify(repository).findAllByOwnerIdAndFilters(77L, numberFilter, statusFilter, balanceFilter, pageable);
    }

    private void authenticate(String login, String authority) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                login, "ignored", List.of(() -> authority));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Card card(Long id, String number, Long ownerId, CardStatus status, Long balance) {
        Card card = new Card();
        card.setId(id);
        card.setNumber(number);
        card.setOwner(new User(ownerId));
        card.setStatus(status);
        card.setBalance(balance);

        return card;
    }
}
