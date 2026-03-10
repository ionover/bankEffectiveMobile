package org.example.bank2.service;

import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.example.bank2.mapper.UserMapper.userMapper;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final BCryptPasswordEncoder encoder;
    private final UserRepository repository;

    public UserService(BCryptPasswordEncoder encoder, UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User getUserById(Long id) {
        return repository.findUserById(id)
                         .orElseThrow(() -> new BadRequestException("Пользователь с ID" + id + " не найден"));
    }

    public User getUserByLogin(String login) {
        return repository.findUserByLogin(login)
                         .orElseThrow(() -> new BadRequestException("Пользователь с login" + login + " не найден"));
    }

    @Transactional
    public User createUser(User user) {
        log.debug("Попросили создать пользователя {}", user);

        Optional<User> olUser = repository.findUserByLogin(user.getLogin());
        if (olUser.isPresent()) {
            throw new BadRequestException("Указанный email '" + user.getLogin() + "' занят. Выберете другой!");
        }

        user.setPassword(encoder.encode(user.getPassword()));

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
