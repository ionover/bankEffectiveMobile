package org.example.bank2.repository;

import org.example.bank2.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findUserByLogin(String username);

    Optional<User> findUserById(Long id);

    User save(User user);

    void deleteById(Long id);
}
