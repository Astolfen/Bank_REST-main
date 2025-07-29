package com.example.bankcards.service;

import com.example.bankcards.dto.AuthDto;
import com.example.bankcards.dto.UserDto;

public interface UserService {
    void registerUser(AuthDto authDto);

    UserDto getUser(String name);

    UserDto updateUser(UserDto userDto);

    void deleteUser(String name);
}
