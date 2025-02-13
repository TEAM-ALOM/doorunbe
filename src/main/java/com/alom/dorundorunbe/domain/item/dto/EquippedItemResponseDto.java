package com.alom.dorundorunbe.domain.item.dto;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.domain.UserItem;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecordItem;

public record EquippedItemResponseDto(
        Long id,
        String name,
        ItemCategory itemCategory,
        Long imageId
) {
    public static EquippedItemResponseDto of(Long id, String name, ItemCategory itemCategory, Long imageId) {
        return new EquippedItemResponseDto(id, name, itemCategory, imageId);
    }

    public static EquippedItemResponseDto from(UserItem userItem) {
        Item item = userItem.getItem();
        return new EquippedItemResponseDto(item.getId(), item.getName(), item.getItemCategory(), item.getImage().getId());
    }

    public static EquippedItemResponseDto from(RunningRecordItem runningRecordItem) {
        Item item = runningRecordItem.getItem();
        return new EquippedItemResponseDto(item.getId(), item.getName(), item.getItemCategory(), item.getImage().getId());
    }
}