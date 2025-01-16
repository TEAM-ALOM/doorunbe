package com.alom.dorundorunbe.domain.item.controller;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.item.dto.ItemResponseDto;
import com.alom.dorundorunbe.domain.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "아이템 목록 조회", description = "카테고리별로 아이템 목록을 반환합니다(소유한 아이템 우선 정렬)")
    public ResponseEntity<List<ItemResponseDto>> fetchItemByCategory(@PathVariable("itemCategory") ItemCategory itemCategory,
                                                                   @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.findItemByCategory(itemCategory, userId));
    }

    @PostMapping("/{itemId}")
    @Operation(summary = "아이템 구매", description = "캐시로 아이템을 구매합니다")
    public ResponseEntity<Void> purchaseItem(@PathVariable("itemId") Long itemId,
                                             @RequestParam("userId") Long userId) {
        itemService.purchaseItem(itemId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{itemId}/equipped")
    @Operation(summary = "아이템 착용", description = "아이템 착용 후 착용 중인 아이템 목록을 반환합니다")
    public ResponseEntity<List<EquippedItemResponseDto>> equippedItem(@PathVariable("itemId") Long itemId,
                                                                   @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.equippedItem(itemId, userId));
    }

    @PostMapping("/{itemId}/unequipped")
    @Operation(summary = "아이템 착용 해제", description = "아이템 착용 해제 후 착용 중인 아이템 목록을 반환합니다")
    public ResponseEntity<List<EquippedItemResponseDto>> unequippedItem(@PathVariable("itemId") Long itemId,
                                                                     @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.unequippedItem(itemId, userId));
    }

    @GetMapping("/equipped")
    @Operation(summary = "착용한 아이템 목록 조회", description = "착용한 아이템 목록을 반환합니다")
    public ResponseEntity<List<EquippedItemResponseDto>> fetchEquippedItemList(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.findEquippedItemList(userId));
    }
}
