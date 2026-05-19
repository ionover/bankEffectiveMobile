package org.example.bank2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.bank2.dto.CardRequest;
import org.example.bank2.dto.CardResponse;
import org.example.bank2.dto.enums.CardStatus;
import org.example.bank2.entity.Card;
import org.example.bank2.entity.User;
import org.example.bank2.service.CardService;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "Cards", description = "Управление банковскими картами")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    @PreAuthorize(HAS_ANY_AUTHORITY)
    @Operation(summary = "Получить список карт", description = "Возвращает постраничный список карт с фильтрами")
    public ResponseEntity<Page<CardResponse>> getAll(@ParameterObject Pageable pageable,
                                                     @Parameter(description = "Фильтр по номеру карты",
                                                             example = "1234567890123456")
                                                     @RequestParam(required = false) String number,
                                                     @Parameter(description = "Фильтр по статусу карты",
                                                             schema = @Schema(allowableValues = {"ACTIVE", "BLOCKED", "FRIEZE"}))
                                                     @RequestParam(required = false) CardStatus status,
                                                     @Parameter(description = "Фильтр по балансу карты", example = "5000")
                                                     @RequestParam(required = false) Long balance) {
        Page<CardResponse> cards = cardService.getAllCards(pageable, number, status, balance);

        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    @PreAuthorize(HAS_ANY_AUTHORITY)
    @Operation(summary = "Получить карту по ID", description = "Возвращает информацию о банковской карте")
    public ResponseEntity<CardResponse> getCard(@Parameter(description = "ID карты", example = "1")
                                                @PathVariable Long id) {
        CardResponse card = cardService.getCardById(id);

        return ResponseEntity.ok(card);
    }

    @PostMapping
    @PreAuthorize(ADMIN_AUTHORITY)
    @Operation(summary = "Создать карту", description = "Создает банковскую карту для пользователя")
    public ResponseEntity<CardResponse> createCard(@RequestBody @Valid CardRequest request) {
        CardResponse card = cardService.createCard(new Card(request.getNumber(), new User(request.getOwner())));

        return ResponseEntity.status(CREATED).body(card);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize(ADMIN_AUTHORITY)
    @Operation(summary = "Обновить статус карты", description = "Изменяет статус банковской карты")
    public ResponseEntity<Objects> updateCard(@RequestBody
                                              @Schema(description = "Новый статус карты", example = "BLOCKED",
                                                      allowableValues = {"ACTIVE", "BLOCKED", "FRIEZE"})
                                              @Valid CardStatus status,
                                              @Parameter(description = "ID карты", example = "1")
                                              @PathVariable Long id) {
        cardService.updateCardStatus(id, status);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    @Operation(summary = "Удалить карту", description = "Удаляет банковскую карту по ID")
    public ResponseEntity<Objects> deleteCard(@Parameter(description = "ID карты", example = "1")
                                              @PathVariable Long id) {
        cardService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
