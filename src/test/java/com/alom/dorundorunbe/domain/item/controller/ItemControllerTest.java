package com.alom.dorundorunbe.domain.item.controller;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.item.dto.ItemResponseDto;
import com.alom.dorundorunbe.domain.item.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("카테고리별 아이템 목록 조회")
    void fetchItemByCategory() throws Exception {
        List<ItemResponseDto> mockItemList = List.of(
                ItemResponseDto.of(1L, "cloth1", 1L, 100L, true),
                ItemResponseDto.of(2L, "cloth2", 2L, 200L, false)
        );

        when(itemService.findItemByCategory(eq(ItemCategory.CLOTHES), eq(1L)))
                .thenReturn(mockItemList);

        mockMvc.perform(get("/items/CLOTHES")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockItemList)));
    }

    @Test
    void purchaseItem() throws Exception {
        mockMvc.perform(post("/items/1")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(itemService).purchaseItem(1L, 1L);
    }

    @Test
    void equippedItem() throws Exception {
        List<EquippedItemResponseDto> equippedItemList = List.of(
                EquippedItemResponseDto.of(1L, "티셔츠", ItemCategory.CLOTHES, 1L)
        );

        when(itemService.equippedItem(1L, 1L)).thenReturn(equippedItemList);

        mockMvc.perform(post("/items/1/equipped")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(equippedItemList)));
    }

    @Test
    void unequippedItem() throws Exception {
        List<EquippedItemResponseDto> equippedItemList = List.of(
                EquippedItemResponseDto.of(2L, "모자", ItemCategory.HAIR, 1L)
        );

        when(itemService.unequippedItem(1L, 1L)).thenReturn(equippedItemList);

        mockMvc.perform(post("/items/1/unequipped")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(equippedItemList)));
    }

    @Test
    void fetchEquippedItemList() throws Exception {
        List<EquippedItemResponseDto> equippedItemList = List.of(
                EquippedItemResponseDto.of(1L, "티셔츠", ItemCategory.CLOTHES, 1L),
                EquippedItemResponseDto.of(2L, "모자", ItemCategory.HAIR, 2L)
        );

        when(itemService.findEquippedItemList(1L)).thenReturn(equippedItemList);

        mockMvc.perform(get("/items/equipped")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(equippedItemList)));
    }
}