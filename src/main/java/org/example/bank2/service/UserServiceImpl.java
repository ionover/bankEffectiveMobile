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

import static org.example.bank2.mapper.UserMapper.userMapper;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final BCryptPasswordEncoder encoder;
    private final UserRepository repository;

    public UserServiceImpl(BCryptPasswordEncoder encoder,
                           UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @Override
    public Page<UserProjection> getAllUsers(Pageable pageable) {
        return repository.findAllByIsDeletedFalse(pageable).map(this::mapToUserProjection);
    }

    @Override
    public UserProjection getUserProjectionById(Long id) {
        User user = repository.findUserByIdAndIsDeletedFalse(id)
                              .orElseThrow(() -> new BadRequestException("Пользователь с ID" + id + " не найден"));

        return mapToUserProjection(user);
    }

    @Override
    public UserProjection getUserProjectionByLogin(String login) {
        return repository.findUserByLoginAndIsDeletedFalse(login)
                         .map(this::mapToUserProjection)
                         .orElseThrow(() -> new BadRequestException("Пользователь с login" + login + " не найден"));
    }

    @Override
    public User getUserByLogin(String login) {
        return repository.findUserByLoginAndIsDeletedFalse(login)
                         .orElseThrow(() -> new BadRequestException("Пользователь с login" + login + " не найден"));
    }

    @Transactional
    @Override
    public UserProjection createUser(User user) {
        log.debug("Попросили создать пользователя {}", user);

        if (repository.existsByLogin(user.getLogin())) {
            throw new BadRequestException("Указанный email '" + user.getLogin() + "' занят. Выберете другой!");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        user = repository.save(user);

        return mapToUserProjection(user);
    }

    @Transactional
    @Override
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
    @Override
    public void deleteById(Long id) {
        User user = getUserById(id);
        user.setIsDeleted(true);
        repository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return repository.findUserByIdAndIsDeletedFalse(id)
                         .orElseThrow(() -> new BadRequestException("Пользователь с ID" + id + " не найден"));
    }

    private UserProjection mapToUserProjection(User user) {
        return userMapper.toProjection(user);
    }
}
