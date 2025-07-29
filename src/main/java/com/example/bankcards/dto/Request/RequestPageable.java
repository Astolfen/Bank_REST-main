package com.example.bankcards.dto.Request;

public record RequestPageable (
        Integer page,
        Integer size,
        String sortField,
        String sortDirection
) {
    public RequestPageable {
        if (page == null) page = 0;
        if (size == null) size = 15;
        if (sortField == null) sortField = "balance";
        if (sortDirection == null) sortDirection = "DESC";
    }
}
