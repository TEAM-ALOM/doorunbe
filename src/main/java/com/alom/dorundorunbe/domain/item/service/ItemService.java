package com.alom.dorundorunbe.domain.item.service;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.domain.UserItem;
import com.alom.dorundorunbe.domain.item.dto.ItemResponseDto;
import com.alom.dorundorunbe.domain.item.repository.ItemRepository;
import com.alom.dorundorunbe.domain.item.repository.UserItemRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;
    private final UserService userService;

    public List<ItemResponseDto> getItemByCategory(ItemCategory itemCategory, Long userId) {

        User user = userService.findById(userId);
        List<Item> itemList = itemRepository.findAllByItemCategory(itemCategory);

        return itemList.stream()
                .map(item -> ItemResponseDto.of(
                        item.getName(),
                        item.getCost(),
                        userItemRepository.findByUserAndItem(user, item)
                ))
                .sorted(Comparator.comparing(ItemResponseDto::owned).reversed())
                .toList();
    }

    public void purchaseItem(Long itemId, Long userId) {
        User user = userService.findById(userId);
        Item item = itemRepository.findById(itemId).orElseThrow();

        if (userItemRepository.findByUserAndItem(user, item)) {
            throw new IllegalArgumentException("이미 소유한 아이템입니다");
        }
        if (user.getCash()<item.getCost()) {
            throw new IllegalArgumentException("잔액이 부족합니다");
        }

        user.updateCash(user.getCash()-item.getCost());
        UserItem userItem = UserItem.builder()
                .user(user)
                .item(item)
                .build();
        userItemRepository.save(userItem);
    }
}
