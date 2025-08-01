package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.Request.RequestExtendPeriod;
import com.example.bankcards.dto.Request.RequestFilterCard;
import com.example.bankcards.dto.Request.RequestPageable;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.response.ResponseListCard;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    //todo заменить на username
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardDto> createCard(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(userDto));
    }

    @PatchMapping("/{cardNumber}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> blockCard(@PathVariable
                                          @Pattern(regexp = "\\d{16}",
                                                  message = "Номер карты должен состоять из 16 цифр")
                                          String cardNumber) {

        cardService.setCardStatus(cardNumber, CardStatus.BLOCKED);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{cardNumber}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activeCard(@PathVariable
                                           @Pattern(regexp = "\\d{16}",
                                                   message = "Номер карты должен состоять из 16 цифр")
                                           String cardNumber) {

        cardService.setCardStatus(cardNumber, CardStatus.ACTIVE);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cardNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCard(@PathVariable
                                           @Pattern(regexp = "\\d{16}",
                                                   message = "Номер карты должен состоять из 16 цифр")
                                           String cardNumber) {
        cardService.deleteCard(cardNumber);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseListCard> getAllCards(@RequestBody RequestFilterCard requestFilterCard) {
        return ResponseEntity.ok(cardService.getAllCards(requestFilterCard));
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<CardDto> getCard(@PathVariable
                                           @Pattern(regexp = "\\d{16}",
                                                   message = "Номер карты должен состоять из 16 цифр")
                                           String cardNumber) {
        return ResponseEntity.ok(cardService.getCard(cardNumber));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseListCard> getUserCard(@PathVariable
                                                     @Size(max = 100, message = "Имя пользователя не должно быть больше 100")
                                                     String username,
                                                        @RequestBody RequestPageable pageable) {
        return ResponseEntity.ok(cardService.getCardsByUser(username,pageable));
    }

    @PatchMapping("/extend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> extendPeriodCard(@RequestBody @Valid RequestExtendPeriod requestExtendPeriod) {
        cardService.extendPeriodCard(requestExtendPeriod);

        return ResponseEntity.ok().build();
    }

}
