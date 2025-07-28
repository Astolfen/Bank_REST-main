package com.example.bankcards.exception;

import com.example.bankcards.dto.error.ErrorMessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private ErrorMessageResponseDto getResponseBody(String errorMessage)  {
        return ErrorMessageResponseDto.builder()
                .dateTime("Date error: " + formatter.format(LocalDate.now()))
                .description(errorMessage)
                .build();
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorMessageResponseDto> handleCardNotFound(CardNotFoundException e) {
       log.error("Exception: CardNotFoundException. " +
               "Exception message: " + e.getMessage());

       return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(getResponseBody(e.getMessage()));
    }
}
