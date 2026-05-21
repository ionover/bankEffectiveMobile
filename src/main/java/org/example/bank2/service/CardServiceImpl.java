package org.example.bank2.service;

import jakarta.transaction.Transactional;
import org.example.bank2.dto.CardRequest;
import org.example.bank2.dto.CardResponse;
import org.example.bank2.dto.UserProjection;
import org.example.bank2.dto.enums.CardStatus;
import org.example.bank2.entity.Card;
import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.exception.ForbiddenException;
import org.example.bank2.repository.CardRepository;
import org.example.bank2.util.CardNumberProtector;
import org.example.bank2.util.CardNumberProtector.CardNumberFilter;
import org.example.bank2.util.CardNumberProtector.ProtectedCardNumber;
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
public class CardServiceImpl implements CardService {

    private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CardRepository repository;
    private final UserService userService;
    private final CardNumberProtector cardNumberProtector;

    public CardServiceImpl(CardRepository repository,
                           UserService userService,
                           CardNumberProtector cardNumberProtector) {
        this.repository = repository;
        this.userService = userService;
        this.cardNumberProtector = cardNumberProtector;
    }

    @Override
    public Page<CardResponse> getAllCards(Pageable pageable, String number, CardStatus status, Long balance) {
        CardNumberFilter numberFilter = cardNumberProtector.filter(number);

        if (isAdmin()) {
            return repository.findAllByFilters(numberFilter.hash(), numberFilter.last4(), status, balance, pageable)
                             .map(this::mapToResponse);
        }

        UserProjection user = userService.getUserProjectionByLogin(getCurrentUserLogin());

        return repository.findAllByOwnerIdAndFilters(user.getId(),
                                                     numberFilter.hash(),
                                                     numberFilter.last4(),
                                                     status,
                                                     balance,
                                                     pageable)
                         .map(this::mapToResponse);
    }

    @Override
    public CardResponse getCardById(Long id) {
        return mapToResponse(getAccessibleCardById(id));
    }

    @Override
    public CardResponse createCard(CardRequest request) {
        User user = userService.getUserById(request.getOwner());
        ProtectedCardNumber protectedNumber = cardNumberProtector.protect(request.getNumber());

        Optional<Card> oCard = repository.findByNumberHash(protectedNumber.hash());
        if (oCard.isPresent()) {
            throw new BadRequestException("Создание карты отклонено");
        }

        Card card = new Card();
        card.setOwner(user);
        card.setNumberEncrypted(protectedNumber.encrypted());
        card.setNumberHash(protectedNumber.hash());
        card.setNumberLast4(protectedNumber.last4());
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

    @Override
    public void deleteById(Long id) {
        log.debug("Попросили удалить карту с ID {}", id);

        getCardEntityById(id);
        repository.deleteById(id);
    }

    @Override
    public void updateCardStatus(Long id, CardStatus status) {
        Card card = getCardEntityById(id);

        card.setStatus(status);

        repository.save(card);
    }

    @Override
    public Card getAccessibleCardById(Long id) {
        if (isAdmin()) {
            return getCardEntityById(id);
        }

        UserProjection user = userService.getUserProjectionByLogin(getCurrentUserLogin());

        return repository.findByIdAndOwnerId(id, user.getId())
                         .orElseThrow(() -> new ForbiddenException("Карта недоступна текущему пользователю"));
    }

    @Transactional
    @Override
    public Card updateCard(Card card) {
        return repository.save(card);
    }

    private Card getCardEntityById(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new BadRequestException("Карта с ID " + id + " не найдена"));
    }

    private CardResponse mapToResponse(Card card) {
        return new CardResponse(
                card.getId(),
                cardNumberProtector.mask(card.getNumberLast4()),
                card.getOwner().getId(),
                card.getValidityPeriod(),
                card.getStatus(),
                card.getBalance()
        );
    }
}
