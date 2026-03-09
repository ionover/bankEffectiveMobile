package org.example.bank2.service;

import org.example.bank2.entity.Card;
import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.example.bank2.dto.enums.Status.ACTIVE;

@Service
public class CardService {

    private final CardRepository repository;
    private final UserService userService;

    public CardService(CardRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public Stream<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        repository.findAll().forEach(cards::add);

        return cards.stream();
    }

    public Card getCardById(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new BadRequestException("Карта с ID" + id + " не найдена!!!"));
    }

    public Card createCard(Card card) {
        User user = userService.getUserById(card.getOwner().getId());

        Optional<Card> oCard = repository.findCardByNumber(card.getNumber());
        if (oCard.isPresent()) {
            throw new BadRequestException("Создание карты отклонено");
        }

        card.setOwner(user);
        card.setStatus(ACTIVE);

        LocalDateTime defaultUsePeriod = LocalDateTime.now()
                                                      .toLocalDate()
                                                      .plusYears(3)
                                                      .plusDays(1)
                                                      .atStartOfDay();
        card.setValidityPeriod(defaultUsePeriod);

        repository.save(card);

        return card;
    }
}
