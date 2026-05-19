package org.example.bank2.service;

import org.example.bank2.entity.User;
import org.example.bank2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void deleteByIdMarksUserAsDeleted() {
        User user = new User(1L);
        when(repository.findUserByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(user));

        userService.deleteById(1L);

        assertTrue(user.getIsDeleted());
        verify(repository).save(user);
        verify(repository, never()).deleteById(anyLong());
    }
}
