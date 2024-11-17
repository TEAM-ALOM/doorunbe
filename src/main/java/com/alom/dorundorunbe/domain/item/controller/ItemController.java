package com.alom.dorundorunbe.domain.item.controller;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.item.dto.ItemResponseDto;
import com.alom.dorundorunbe.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemCategory}")
    public ResponseEntity<List<ItemResponseDto>> getItemByCategory(@PathVariable("itemCategory") ItemCategory itemCategory,
                                                                   @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.getItemByCategory(itemCategory, userId));
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<Void> purchaseItem(@PathVariable("itemId") Long itemId,
                                             @RequestParam("userId") Long userId) {
        itemService.purchaseItem(itemId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{itemId}/equipped")
    public ResponseEntity<List<EquippedItemResponseDto>> equippedItem(@PathVariable("itemId") Long itemId,
                                                                   @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.equippedItem(itemId, userId));
    }

    @PostMapping("/{itemId}/unequipped")
    public ResponseEntity<List<EquippedItemResponseDto>> unequippedItem(@PathVariable("itemId") Long itemId,
                                                                     @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.unequippedItem(itemId, userId));
    }

    @GetMapping("/equipped")
    public ResponseEntity<List<EquippedItemResponseDto>> getEquippedItem(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.getEquippedItem(userId));
    }
}
