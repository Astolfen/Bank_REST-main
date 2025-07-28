package com.example.bankcards.service;

import com.example.bankcards.dto.RoleDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserPrincipalServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserPrincipalService userPrincipalService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setUsername("ivan");
        sampleUser.setPassword("secret");
        sampleUser.setRoles(Arrays.asList(
                new Role(1L, "ADMIN"),
                new Role(2L, "USER")
        ));
    }

    @Test
    void testLoadUserByUsername_UserExists_ReturnsUserPrincipal() {
        when(userRepository.findByUsername("ivan")).thenReturn(Optional.of(sampleUser));

        UserPrincipal principal = userPrincipalService.loadUserByUsername("ivan");

        verify(userRepository, times(1)).findByUsername("ivan");

        assertEquals("ivan", principal.getUsername());
        assertEquals("secret", principal.getPassword());

        assertFalse(principal.getRoles().isEmpty());
        assertTrue(principal.getRoles().contains(new RoleDto( "USER")));
        assertTrue(principal.getRoles().contains(new RoleDto( "ADMIN")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound_ThrowsException() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userPrincipalService.loadUserByUsername("unknown")
        );

        assertEquals("Username not found unknown", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("unknown");
    }
}
