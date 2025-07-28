package com.example.bankcards.service.impl;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.GenerateUtils;
import com.example.bankcards.util.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    private final GenerateUtils generateUtils;

    public CardDto createCard(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDto.getUsername() + " not found"));

        Card card = new Card();
        card.setExpirationDate(LocalDate.now().plusYears(4));
        card.setStatus(CardStatus.ACTIVE);
        card.setBalance(BigDecimal.valueOf(0, 0));
        card.setUser(user);

        String gen_number = generateUtils.generateCardNumber();
        while (cardRepository.findByNumber(gen_number).isPresent()) {
            gen_number = generateUtils.generateCardNumber();
        }

        card.setNumber(gen_number);

        cardRepository.save(card);

        return CardMapper.toDto(card);
    }

    public void extendPeriodCard(String number_card, LocalDate endDate) {
        if (endDate.isAfter(LocalDate.now())) {
            Card card = cardRepository.findByNumber(number_card)
                    .orElseThrow(() -> new CardNotFoundException(number_card + " not found"));

            card.setStatus(CardStatus.ACTIVE);
            card.setExpirationDate(endDate);

            cardRepository.save(card);
        } else {
            throw new RuntimeException("Date before now" + endDate);
        }
    }

    public void setCardStatus(String number_card, CardStatus status) {
        Card card = cardRepository.findByNumber(number_card)
                .orElseThrow(() -> new CardNotFoundException(number_card + " not found"));

        card.setStatus(status);

        cardRepository.save(card);
    }

    public void deleteCard(String number_card) {
        Card card = cardRepository.findByNumber(number_card)
                .orElseThrow(() -> new CardNotFoundException(number_card + " not found"));

        cardRepository.delete(card);
    }
}
