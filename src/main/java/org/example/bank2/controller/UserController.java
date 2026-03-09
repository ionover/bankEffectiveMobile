package org.example.bank2.controller;

import jakarta.validation.Valid;
import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.User;
import org.example.bank2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.example.bank2.mapper.UserMapper.userMapper;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<List<User>> getAllUsers() {
        Stream<User> users = userService.getAllUsers();

        return ResponseEntity.ok(users.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<User> createUser(@RequestBody @Validated(OnCreate.class) UserRequest createUserRequest) {
        User user = userService.createUser(userMapper.toEntity(createUserRequest));

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserRequest updateUserRequest, @PathVariable Long id) {
        User user = userService.updateUser(id, updateUserRequest);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    public ResponseEntity<Objects> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
