package com.example.bankcards.service;


import com.example.bankcards.dto.AuthDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private AuthDto authDto;

    @BeforeEach
    void setUp() {
        authDto = AuthDto.builder()
                .username("daniil")
                .password("123456").build();
    }

    @Test
    void testRegisterUser_Success() {
        Role userRole = new Role(1L, "USER");
        when(roleRepository.findByName("USER"))
                .thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");


        userService.registerUser(authDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository,times(1)).save(userCaptor.capture());
        verify(roleRepository, times(1)).findByName("USER");
        verify(passwordEncoder, times(1)).encode("123456");

        User savedUser = userCaptor.getValue();

        assertEquals("daniil", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertTrue(savedUser.getRoles().contains(userRole));
    }

    @Test
    void testRegisterUser_RoleNotFound_ThrowsException() {
        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(authDto)
        );

        assertEquals("Role USER not found", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
