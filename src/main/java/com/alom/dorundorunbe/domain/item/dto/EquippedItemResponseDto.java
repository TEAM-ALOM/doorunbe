package com.alom.dorundorunbe.domain.item.dto;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;

public record EquippedItemResponseDto(
        Long id,
        String name,
        ItemCategory itemCategory,
        Long imageId
) {
    public static EquippedItemResponseDto of(Long id, String name, ItemCategory itemCategory, Long imageId) {
        return new EquippedItemResponseDto(id, name, itemCategory, imageId);
    }
}