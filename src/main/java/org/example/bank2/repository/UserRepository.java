package org.example.bank2.repository;

import org.example.bank2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByIsDeletedFalse(Pageable pageable);

    Optional<User> findUserByIdAndIsDeletedFalse(Long id);

    Optional<User> findUserByLoginAndIsDeletedFalse(String login);

    boolean existsByLogin(String login);
}
