package org.example.bank2.controller;

import jakarta.validation.Valid;
import org.example.bank2.dto.CardRequest;
import org.example.bank2.dto.enums.Status;
import org.example.bank2.entity.Card;
import org.example.bank2.entity.User;
import org.example.bank2.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.example.bank2.security.Authorities.ADMIN_AUTHORITY;
import static org.example.bank2.security.Authorities.HAS_ANY_AUTHORITY;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    @PreAuthorize(HAS_ANY_AUTHORITY)
    public ResponseEntity<Page<Card>> getAll(Pageable pageable) {
        Page<Card> cards = cardService.getAllCards(pageable);

        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    @PreAuthorize(HAS_ANY_AUTHORITY)
    public ResponseEntity<Card> getCard(@PathVariable Long id) {
        Card card = cardService.getCardById(id);

        return ResponseEntity.ok(card);
    }

    @PostMapping
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<Card> createCard(@RequestBody @Valid CardRequest request) {
        Card card = cardService.createCard(new Card(request.getNumber(), new User(request.getOwner())));

        return ResponseEntity.status(CREATED).body(card);
    }

    @PatchMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<Objects> updateCard(@RequestBody @Valid Status status, @PathVariable Long id) {
        cardService.updateCardStatus(id, status);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<Objects> deleteCard(@PathVariable Long id) {
        cardService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
