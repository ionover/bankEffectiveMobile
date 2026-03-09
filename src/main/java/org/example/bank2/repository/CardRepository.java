package org.example.bank2.repository;

import org.example.bank2.entity.Card;
import org.springframework.data.repository.CrudRepository;


public interface CardRepository extends CrudRepository<Card, Long> {

}
