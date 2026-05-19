package org.example.bank2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.bank2.dto.UserProjection;
import org.example.bank2.dto.UserRequest;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.example.bank2.mapper.UserMapper.userMapper;
import static org.example.bank2.security.Authorities.ADMIN_AUTHORITY;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Управление пользователями")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize(ADMIN_AUTHORITY)
    @Operation(summary = "Получить список пользователей", description = "Возвращает постраничный список пользователей")
    public ResponseEntity<Page<UserProjection>> getAllUsers(@ParameterObject Pageable pageable) {
        Page<UserProjection> users = userService.getAllUsers(pageable);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает информацию о пользователе")
    public ResponseEntity<UserProjection> getUser(@Parameter(description = "ID пользователя", example = "1")
                                                  @PathVariable Long id) {
        UserProjection user = userService.getUserProjectionById(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    @PreAuthorize(ADMIN_AUTHORITY)
    @Operation(summary = "Создать пользователя", description = "Создает нового пользователя")
    public ResponseEntity<UserProjection> createUser(@RequestBody @Validated(OnCreate.class) UserRequest createUser) {
        UserProjection user = userService.createUser(userMapper.toEntity(createUser));

        return ResponseEntity.status(CREATED).body(user);
    }

    @PatchMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    @Operation(summary = "Обновить пользователя", description = "Обновляет данные пользователя по ID")
    public ResponseEntity<UserProjection> updateUser(@RequestBody @Valid UserRequest updateUserRequest,
                                                     @Parameter(description = "ID пользователя", example = "1")
                                                     @PathVariable Long id) {
        validateUserUpdateDto(updateUserRequest);

        UserProjection user = userService.updateUser(id, updateUserRequest);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ADMIN_AUTHORITY)
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по ID")
    public ResponseEntity<Objects> deleteUser(@Parameter(description = "ID пользователя", example = "1")
                                              @PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private void validateUserUpdateDto(@Valid UserRequest updateUserRequest) {
        if (updateUserRequest.getLogin() != null) {
            throw new BadRequestException("Логин пользователя менять нельзя!!!");
        }

        if (updateUserRequest.getPassword() == null) {
            return;
        }

        if (updateUserRequest.getPassword().isEmpty()) {
            throw new BadRequestException("Пароль пользователя не может быть пустым!!!");
        }
    }
}
