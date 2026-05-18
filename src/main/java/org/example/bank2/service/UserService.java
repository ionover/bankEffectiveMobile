package org.example.bank2.service;

import org.example.bank2.dto.UserProjection;
import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserProjection> getAllUsers(Pageable pageable);

    UserProjection getUserProjectionById(Long id);

    UserProjection getUserProjectionByLogin(String login);

    UserProjection createUser(User user);

    UserProjection updateUser(Long id, UserRequest updateUserRequest);

    void deleteById(Long id);

    User getUserById(Long id);
}
