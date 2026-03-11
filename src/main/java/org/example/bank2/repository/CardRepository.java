package org.example.bank2.repository;

import org.example.bank2.entity.Card;
import org.example.bank2.dto.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("""
            select c from Card c
            where (:number is null or c.number like cast(concat('%', :number, '%') as string))
              and (:status is null or c.status = :status)
              and (:balance is null or c.balance = :balance)
            """)
    Page<Card> findAllByFilters(@Param("number") String number,
                                @Param("status") CardStatus status,
                                @Param("balance") Long balance,
                                Pageable pageable);

    @Query("""
            select c from Card c
            where c.owner.id = :ownerId
              and (:number is null or c.number like cast(concat('%', :number, '%') as string))
              and (:status is null or c.status = :status)
              and (:balance is null or c.balance = :balance)
            """)
    Page<Card> findAllByOwnerIdAndFilters(@Param("ownerId") Long ownerId,
                                          @Param("number") String number,
                                          @Param("status") CardStatus status,
                                          @Param("balance") Long balance,
                                          Pageable pageable);

    Optional<Card> findByIdAndOwnerId(Long id, Long ownerId);

    Optional<Card> findByNumber(String number);
}
