package com.alom.dorundorunbe.domain.item.dto;

public record ItemResponseDto(
        Long itemId,
        String name,
        Long cost,
        Boolean owned
) {
    public static ItemResponseDto of(Long itemId, String name, Long cost, Boolean owned) {
        return new ItemResponseDto(itemId, name, cost, owned);
    }
}