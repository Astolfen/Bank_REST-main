package com.example.bankcards.service.impl;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.Request.RequestExtendPeriod;
import com.example.bankcards.dto.Request.RequestFilterCard;
import com.example.bankcards.dto.Request.RequestPageable;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.response.ResponseListCard;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.GenerateUtils;
import com.example.bankcards.util.mapper.CardMapper;
import com.example.bankcards.util.specification.CardSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    private final GenerateUtils generateUtils;

    public CardDto createCard(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException(userDto.getUsername() + " not found"));

        Card card = new Card();
        card.setExpirationDate(LocalDate.now().plusYears(4));
        card.setStatus(CardStatus.ACTIVE);
        card.setBalance(BigDecimal.valueOf(0, 0));
        card.setUser(user);

        String genNumber = generateUtils.generateCardNumber();
        while (cardRepository.findByNumber(genNumber).isPresent()) {
            genNumber = generateUtils.generateCardNumber();
        }

        card.setNumber(genNumber);

        cardRepository.save(card);

        return CardMapper.toDto(card);
    }

    public void extendPeriodCard(RequestExtendPeriod requestExtendPeriod) {
        String cardNumber = requestExtendPeriod.getNumber();
        LocalDate endDate = requestExtendPeriod.getEndDate();

        if (endDate.isAfter(LocalDate.now())) {
            Card card = cardRepository.findByNumber(cardNumber).orElseThrow(() -> new CardNotFoundException(cardNumber + " not found"));

            card.setStatus(CardStatus.ACTIVE);
            card.setExpirationDate(endDate);

            cardRepository.save(card);
        } else {
            throw new RuntimeException("Date before now" + endDate);
        }
    }

    public void setCardStatus(String cardNumber, CardStatus status) {
        Card card = cardRepository.findByNumber(cardNumber).orElseThrow(() -> new CardNotFoundException(cardNumber + " not found"));

        card.setStatus(status);

        cardRepository.save(card);
    }

    public void deleteCard(String cardNumber) {
        Card card = cardRepository.findByNumber(cardNumber).orElseThrow(() -> new CardNotFoundException(cardNumber + " not found"));

        cardRepository.delete(card);
    }

    @Override
    public ResponseListCard getAllCards(RequestFilterCard requestFilterCard) {
        Pageable pageable = PageRequest.of(requestFilterCard.page(), requestFilterCard.size(),
                Sort.by(Sort.Direction.fromString(requestFilterCard.sortDirection()), requestFilterCard.sortField()));

        Specification<Card> spec = CardSpecification.getSpecification(requestFilterCard);

        Page<Card> cards = cardRepository.findAll(spec, pageable);

        return new ResponseListCard(cards.getContent().stream().map(CardMapper::toDto).toList(),
                cards.getPageable().getPageNumber(),
                cards.getPageable().getPageSize(),
                cards.getTotalPages(),
                cards.getTotalElements());
    }

    @Override
    public CardDto getCard(String cardNumber) {
        Card card = cardRepository.findByNumber(cardNumber).orElseThrow(() -> new CardNotFoundException(cardNumber + " not found"));

        return CardMapper.toDto(card);
    }

    @Override
    public ResponseListCard getCardsByUser(String username, RequestPageable requestPageable) {
        Pageable pageable = PageRequest.of(requestPageable.page(), requestPageable.size(),
                Sort.by(Sort.Direction.fromString(requestPageable.sortDirection()), requestPageable.sortField()));

        Page<Card> cards = cardRepository.findByUser_Username(username, pageable);

        return new ResponseListCard(cards.getContent().stream().map(CardMapper::toDto).toList(),
                cards.getPageable().getPageNumber(),
                cards.getPageable().getPageSize(),
                cards.getTotalPages(),
                cards.getTotalElements());
    }
}
