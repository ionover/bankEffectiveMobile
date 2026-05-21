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
            where (:numberHash is null or c.numberHash = :numberHash)
              and (:numberLast4 is null or c.numberLast4 = :numberLast4)
              and (:status is null or c.status = :status)
              and (:balance is null or c.balance = :balance)
            """)
    Page<Card> findAllByFilters(@Param("numberHash") String numberHash,
                                @Param("numberLast4") String numberLast4,
                                @Param("status") CardStatus status,
                                @Param("balance") Long balance,
                                Pageable pageable);

    @Query("""
            select c from Card c
            where c.owner.id = :ownerId
              and (:numberHash is null or c.numberHash = :numberHash)
              and (:numberLast4 is null or c.numberLast4 = :numberLast4)
              and (:status is null or c.status = :status)
              and (:balance is null or c.balance = :balance)
            """)
    Page<Card> findAllByOwnerIdAndFilters(@Param("ownerId") Long ownerId,
                                          @Param("numberHash") String numberHash,
                                          @Param("numberLast4") String numberLast4,
                                          @Param("status") CardStatus status,
                                          @Param("balance") Long balance,
                                          Pageable pageable);

    Optional<Card> findByIdAndOwnerId(Long id, Long ownerId);

    Optional<Card> findByNumberHash(String numberHash);
}
