package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;

public class CardMapper {

    public static CardDto toDto(Card card){
        return CardDto.builder()
                .number(card.getNumber())
                .expirationDate(card.getExpirationDate())
                .status(card.getStatus())
                .balance(card.getBalance())
                .user(UserMapper.toDto(card.getUser()))
                .build();
    }
}
