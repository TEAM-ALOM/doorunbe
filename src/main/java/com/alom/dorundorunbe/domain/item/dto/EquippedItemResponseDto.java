package com.alom.dorundorunbe.domain.item.dto;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;

public record EquippedItemResponseDto(
        Long itemId,
        String name,
        ItemCategory itemCategory
) {
    public static EquippedItemResponseDto of(Long itemId, String name, ItemCategory itemCategory) {
        return new EquippedItemResponseDto(itemId, name, itemCategory);
    }
}