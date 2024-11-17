package com.alom.dorundorunbe.domain.item.dto;

public record ItemResponseDto(
        String name,
        Long cost,
        Boolean owned
) {
    public static ItemResponseDto of(String name, Long cost, Boolean owned) {
        return new ItemResponseDto(name, cost, owned);
    }
}
