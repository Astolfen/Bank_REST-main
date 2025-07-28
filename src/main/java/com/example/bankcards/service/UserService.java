package com.example.bankcards.service;

import com.example.bankcards.dto.AuthDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void registerUser(AuthDto authDto);
}
