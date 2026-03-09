package org.example.bank2.service;

import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Stream<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add);

        return users.stream();
    }

    public User getUserById(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new BadRequestException("Пользователь с ID" + id + " не найден"));
    }

    public User createUser(User user) {
        user.setAdmin(false);

        return repository.save(user);
    }

    public void deleteById(Long id) {
        User user = getUserById(id);

        if (user.isAdmin()) {
            throw new BadRequestException("Невозможно удалить администратора!!!");
        }

        repository.deleteById(id);
    }
}
