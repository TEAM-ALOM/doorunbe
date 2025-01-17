package com.alom.dorundorunbe.domain.item.dto;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;

public record ItemRequestDto(
        String name,
        ItemCategory itemCategory,
        Long cost
) {
    public static ItemRequestDto of(String name, ItemCategory itemCategory, Long cost) {
        return new ItemRequestDto(name, itemCategory, cost);
    }
}
