package org.example.bank2.controller;

import org.example.bank2.dto.UserRequest;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class UserControllerTest {

    @Test
    void updateUserThrowsWhenLoginIsProvided() {
        UserController controller = new UserController(mock(UserService.class));
        UserRequest request = new UserRequest();
        request.setLogin("new-login");

        assertThrows(BadRequestException.class, () -> controller.updateUser(request, 1L));
    }
}
