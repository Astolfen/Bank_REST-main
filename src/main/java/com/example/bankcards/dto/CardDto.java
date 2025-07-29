package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.util.serializer.CardNumberMaskSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    @NotBlank
    @Pattern(regexp = "\\d{16}",
            message = "Номер карты должен состоять из 16 цифр")
    @JsonSerialize(using = CardNumberMaskSerializer.class)
    private String number;

    @NotNull
    private LocalDate expirationDate;

    @NotNull
    private CardStatus status;

    @NotNull
    @Digits(integer = 17,fraction = 2)
    @PositiveOrZero
    private BigDecimal balance;

    @NotNull
    private UserDto user;
}
