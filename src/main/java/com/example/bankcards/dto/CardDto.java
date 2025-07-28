package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 16, max = 16,message = "Длина номера должна быть 16.")
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
