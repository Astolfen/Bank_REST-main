package com.example.bankcards.service.impl;

import com.example.bankcards.dto.AuthDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(AuthDto authDto) {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User user = new User();
        user.setUsername(authDto.getUsername());
        user.setPassword(passwordEncoder.encode(authDto.getPassword()));
        user.setRoles(List.of(role));

        userRepository.save(user);
    }
}
