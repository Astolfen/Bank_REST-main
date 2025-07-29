package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {

    @NotBlank
    @Size(min = 6, max = 100, message = "Имя должно быть от 6 до 100 символов")
    private String username;

    @NotBlank
    @Size(min = 8, max = 50, message = "Пароль должен быть от 8 до 50 символов")
    private String password;
}
