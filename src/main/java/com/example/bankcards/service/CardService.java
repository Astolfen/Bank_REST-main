package com.example.bankcards.service;


import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.Request.RequestExtendPeriod;
import com.example.bankcards.dto.Request.RequestFilterCard;
import com.example.bankcards.dto.Request.RequestPageable;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.response.ResponseListCard;
import com.example.bankcards.entity.CardStatus;

public interface CardService {
    CardDto createCard(UserDto userDto);

    void extendPeriodCard(RequestExtendPeriod requestExtendPeriod);

    void setCardStatus(String cardNumber, CardStatus status);

    void deleteCard(String cardNumber);

    ResponseListCard getAllCards(RequestFilterCard requestFilterCard);

    CardDto getCard(String cardNumber);

    ResponseListCard getCardsByUser(String username, RequestPageable pageable);

}
