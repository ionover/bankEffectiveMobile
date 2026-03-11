package org.example.bank2.controller;

import org.example.bank2.dto.UserRequest;
import org.example.bank2.exception.BadRequestException;
import org.example.bank2.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {

    @Test
    void updateUserThrowsWhenLoginIsProvided() {
        UserController controller = new UserController(new UserService(null, null));
        UserRequest request = new UserRequest();
        request.setLogin("new-login");

        assertThrows(BadRequestException.class, () -> controller.updateUser(request, 1L));
    }
}
