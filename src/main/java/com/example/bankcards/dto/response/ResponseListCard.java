package com.example.bankcards.dto.response;

import com.example.bankcards.dto.CardDto;

import java.util.List;

public record ResponseListCard(
        List<CardDto> cards,

        int pageNumber,

        int elementToPage,

        int countPage,

        long countElements
) {
}
