package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.RoleDto;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(role -> new RoleDto(role.getName()))
                        .collect(Collectors.toList()))
                .build();
    }
}
