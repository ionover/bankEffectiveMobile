package org.example.bank2.repository;

import org.example.bank2.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String username);
}
