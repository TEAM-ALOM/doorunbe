package com.alom.dorundorunbe.domain.item.dto;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;

public record EquippedItemResponseDto(
        Long id,
        String name,
        ItemCategory itemCategory
) {
    public static EquippedItemResponseDto of(Long id, String name, ItemCategory itemCategory) {
        return new EquippedItemResponseDto(id, name, itemCategory);
    }
}