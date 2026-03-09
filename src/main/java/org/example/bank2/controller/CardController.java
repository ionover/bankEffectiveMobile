package org.example.bank2.controller;

import jakarta.validation.Valid;
import org.example.bank2.dto.CardRequest;
import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.Card;
import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.example.bank2.mapper.CardMapper.cardMapper;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAll() {
        Stream<Card> cards = cardService.getAllCards();

        return ResponseEntity.ok(cards.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable Long id) {
        Card card = cardService.getCardById(id);

        return ResponseEntity.ok(card);
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody @Valid CardRequest userRequest) {
        validateOwnerDto(userRequest.getOwner());

        Card card = cardService.createCard(cardMapper.toEntity(userRequest));

        return ResponseEntity.ok(card);
    }

//    @PatchMapping
//    public ResponseEntity<User> updateUser(@RequestBody @Valid UserRequest userRequest) {
//        //TODO: нужно реализовать
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Objects> deleteUser(@PathVariable Long id) {
//        userService.deleteById(id);
//
//        return ResponseEntity.noContent().build();
//    }

    private void validateOwnerDto(User owner) {
        if (owner.getId() == null) {
            throw new BadRequestException("Обязательно указывать ID владельца карты");
        }
    }
}
