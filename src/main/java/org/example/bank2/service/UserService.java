package org.example.bank2.service;

import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.example.bank2.mapper.UserMapper.userMapper;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

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

    public User getUserByLogin(String login) {
        return repository.findByLogin(login)
                         .orElseThrow(() -> new BadRequestException("Пользователь с login" + login + " не найден"));
    }

    public User createUser(User user) {
        log.debug("Попросили создать пользователя {}", user);

        return repository.save(user);
    }

    public User updateUser(Long id, UserRequest updateUserRequest) {
        User user = getUserById(id);

        userMapper.updateUserFromDto(updateUserRequest, user);

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
