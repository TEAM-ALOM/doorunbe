package com.alom.dorundorunbe.domain.item.dto;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;

public record ItemRequestDto(
        String name,
        ItemCategory itemCategory,
        Long imageId,
        Long cost
) {
}
