package org.example.bank2.service;

import org.example.bank2.dto.UserProjection;
import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.User;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public UserService(BCryptPasswordEncoder encoder,
                       UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    public Page<UserProjection> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToUserProjection);
    }

    public UserProjection getUserProjectionById(Long id) {
        User user = repository.findUserById(id)
                              .orElseThrow(() -> new BadRequestException("Пользователь с ID" + id + " не найден"));

        return mapToUserProjection(user);
    }

    public User getUserByLogin(String login) {
        return repository.findUserByLogin(login)
                         .orElseThrow(() -> new BadRequestException("Пользователь с login" + login + " не найден"));
    }

    @Transactional
    public UserProjection createUser(User user) {
        log.debug("Попросили создать пользователя {}", user);

        Optional<User> olUser = repository.findUserByLogin(user.getLogin());
        if (olUser.isPresent()) {
            throw new BadRequestException("Указанный email '" + user.getLogin() + "' занят. Выберете другой!");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        user = repository.save(user);

        return mapToUserProjection(user);
    }

    @Transactional
    public UserProjection updateUser(Long id, UserRequest updateUserRequest) {
        if (updateUserRequest.getPassword() == null) {
            updateUserRequest.setPassword(encoder.encode(updateUserRequest.getPassword()));
        }

        User user = getUserById(id);
        userMapper.updateUserFromDto(updateUserRequest, user);

        user = repository.save(user);

        return mapToUserProjection(user);
    }

    @Transactional
    public void deleteById(Long id) {
//        User user = getUserById(id);
//
//        if (user.getIsAdmin()) {
//            throw new BadRequestException("Невозможно удалить администратора!!!");
//        }

        repository.deleteById(id);
    }

    public User getUserById(Long id) {
        return repository.findUserById(id)
                         .orElseThrow(() -> new BadRequestException("Пользователь с ID" + id + " не найден"));
    }

    private UserProjection mapToUserProjection(User user) {
        return userMapper.toProjection(user);
    }
}
