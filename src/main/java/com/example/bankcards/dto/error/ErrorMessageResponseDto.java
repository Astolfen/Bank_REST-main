package com.example.bankcards.dto.error;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponseDto {

    @NotNull
    private String dateTime;

    @NotBlank
    private String description;
}
