package com.alom.dorundorunbe.domain.item.dto;

public record ItemResponseDto(
        Long id,
        String name,
        Long imageId,
        Long cost,
        Boolean owned
) {
    public static ItemResponseDto of(Long itemId, String name, Long imageId, Long cost, Boolean owned) {
        return new ItemResponseDto(itemId, name, imageId, cost, owned);
    }
}