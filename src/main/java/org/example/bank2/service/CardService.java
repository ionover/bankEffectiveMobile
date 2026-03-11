package org.example.bank2.service;

import jakarta.transaction.Transactional;
import org.example.bank2.dto.CardResponse;
import org.example.bank2.dto.enums.CardStatus;
import org.example.bank2.entity.Card;
import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.exception.ForbiddenException;
import org.example.bank2.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.example.bank2.dto.enums.CardStatus.ACTIVE;
import static org.example.bank2.security.Authorities.getCurrentUserLogin;
import static org.example.bank2.security.Authorities.isAdmin;

@Service
public class CardService {

    private final Logger log = LoggerFactory.getLogger(CardService.class);

    private final CardRepository repository;
    private final UserService userService;

    public CardService(CardRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public Page<CardResponse> getAllCards(Pageable pageable) {
        if (isAdmin()) {
            return repository.findAll(pageable).map(this::mapToResponse);
        }

        User user = userService.getUserByLogin(getCurrentUserLogin());

        return repository.findAllByOwnerId(user.getId(), pageable).map(this::mapToResponse);
    }

    public CardResponse getCardById(Long id) {
        return mapToResponse(getAccessibleCardById(id));
    }

    public CardResponse createCard(Card card) {
        User user = userService.getUserById(card.getOwner().getId());

        Optional<Card> oCard = repository.findByNumber(card.getNumber());
        if (oCard.isPresent()) {
            throw new BadRequestException("Создание карты отклонено");
        }

        card.setOwner(user);
        card.setStatus(ACTIVE);
        card.setBalance(0L);

        LocalDateTime defaultUsePeriod = LocalDateTime.now()
                                                      .toLocalDate()
                                                      .plusYears(3)
                                                      .plusDays(1)
                                                      .atStartOfDay();
        card.setValidityPeriod(defaultUsePeriod);

        repository.save(card);

        return mapToResponse(card);
    }

    public void deleteById(Long id) {
        log.debug("Попросили удалить карту с ID {}", id);

        getCardEntityById(id);
        repository.deleteById(id);
    }

    public void updateCardStatus(Long id, CardStatus status) {
        Card card = getCardEntityById(id);

        card.setStatus(status);

        repository.save(card);
    }

    public Card getAccessibleCardById(Long id) {
        if (isAdmin()) {
            return getCardEntityById(id);
        }

        User user = userService.getUserByLogin(getCurrentUserLogin());

        return repository.findByIdAndOwnerId(id, user.getId())
                         .orElseThrow(() -> new ForbiddenException("Карта недоступна текущему пользователю"));
    }

    @Transactional
    public Card topOnBalance(Card card) {
        return repository.save(card);
    }

    private Card getCardEntityById(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new BadRequestException("Карта с ID " + id + " не найдена"));
    }

    private CardResponse mapToResponse(Card card) {
        return new CardResponse(
                card.getId(),
                maskCardNumber(card.getNumber()),
                card.getOwner().getId(),
                card.getValidityPeriod(),
                card.getStatus(),
                card.getBalance()
        );
    }

    private String maskCardNumber(String number) {
        return "**** **** **** " + number.substring(number.length() - 4);
    }
}
