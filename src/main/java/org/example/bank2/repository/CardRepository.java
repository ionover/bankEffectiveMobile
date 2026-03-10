package org.example.bank2.repository;

import org.example.bank2.entity.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Long> {

    Optional<Card> findCardByNumber(String number);
}

