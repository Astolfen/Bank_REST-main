package com.example.bankcards.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestExtendPeriod {
    @NotBlank
    @Pattern(regexp = "\\d{16}",
            message = "Номер карты должен состоять из 16 цифр")
    private String number;

    @NotNull
    private LocalDate endDate;
}
