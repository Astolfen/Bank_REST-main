package com.example.bankcards.dto.Request;

import com.example.bankcards.entity.CardStatus;

import java.math.BigDecimal;

public record RequestFilterCard(
        Integer page,
        Integer size,
        String sortField,
        String sortDirection,

        CardStatus status,
        String number,
        String username,
        BigDecimal lowerLimitBalance,
        BigDecimal upperLimitBalance
) {
    public RequestFilterCard {
        if (page == null) page = 0;
        if (size == null) size = 15;
        if (sortField == null) sortField = "balance";
        if (sortDirection == null) sortDirection = "DESC";
    }
}