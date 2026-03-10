package org.example.bank2.repository;

import org.example.bank2.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Page<Card> findAllByOwnerId(Long id, Pageable pageable);

    Optional<Card> findByNumber(String number);
}

