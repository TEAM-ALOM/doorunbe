package com.alom.dorundorunbe.domain.item.controller;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.dto.ItemResponseDto;
import com.alom.dorundorunbe.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemCategory}")
    public ResponseEntity<List<ItemResponseDto>> getItemByCategory(@PathVariable("itemCategory") ItemCategory itemCategory,
                                                                   @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(itemService.getItemByCategory(itemCategory, userId));
    }
}
