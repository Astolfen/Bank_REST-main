package com.example.bankcards.dto.error;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponseDto {

    @NotBlank
    private String dateTime;

    @NotBlank
    private String description;
}
