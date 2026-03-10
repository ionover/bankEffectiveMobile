package org.example.bank2.controller;

import jakarta.validation.Valid;
import org.example.bank2.dto.CardRequest;
import org.example.bank2.dto.CardResponse;
import org.example.bank2.dto.CardStatusUpdateRequest;
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
import static org.example.bank2.security.Authorities.USER;
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
    public ResponseEntity<Page<CardResponse>> getAll(Pageable pageable) {
        Page<CardResponse> cards = cardService.getAllCards(pageable);

        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    @PreAuthorize(HAS_ANY_AUTHORITY)
    public ResponseEntity<CardResponse> getCard(@PathVariable Long id) {
        CardResponse card = cardService.getCardById(id);

        return ResponseEntity.ok(card);
    }

    @PostMapping
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<CardResponse> createCard(@RequestBody @Valid CardRequest request) {
        CardResponse card = cardService.createCard(new Card(request.getNumber(), new User(request.getOwner())));

        return ResponseEntity.status(CREATED).body(card);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<Objects> updateCard(@RequestBody @Valid CardStatusUpdateRequest request,
                                              @PathVariable Long id) {
        cardService.updateCardStatus(id, request.getStatus());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block-request")
    @PreAuthorize("hasAuthority('" + USER + "')")
    public ResponseEntity<Objects> requestBlock(@PathVariable Long id) {
        cardService.requestCardBlock(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<Objects> deleteCard(@PathVariable Long id) {
        cardService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
