package com.alom.dorundorunbe.domain.item.service;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.domain.UserItem;
import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.item.dto.ItemRequestDto;
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

    public void createItem(ItemRequestDto dto) {
        Item item = Item.builder()
                .name(dto.name())
                .itemCategory(dto.itemCategory())
                .cost(dto.cost())
                .build();

        itemRepository.save(item);
    }

    public void updateItem(Long itemId, ItemRequestDto dto) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        item.update(dto.name(), dto.itemCategory(), dto.cost());
    }

    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        itemRepository.delete(item);
    }

    public List<ItemResponseDto> findItemByCategory(ItemCategory itemCategory, Long userId) {

        User user = userService.findById(userId);
        List<Item> itemList = itemRepository.findAllByItemCategory(itemCategory);

        return itemList.stream()
                .map(item -> ItemResponseDto.of(
                        item.getId(),
                        item.getName(),
                        item.getCost(),
                        userItemRepository.existsByUserAndItem(user, item)
                ))
                .sorted(Comparator.comparing(ItemResponseDto::owned).reversed())
                .toList();
    }

    public void purchaseItem(Long itemId, Long userId) {
        User user = userService.findById(userId);
        Item item = itemRepository.findById(itemId).orElseThrow();

        if (userItemRepository.existsByUserAndItem(user, item)) {
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

    public List<EquippedItemResponseDto> equippedItem(Long itemId, Long userId) {
        User user = userService.findById(userId);
        Item item = itemRepository.findById(itemId).orElseThrow();
        UserItem userItem = userItemRepository.findByUserAndItem(user, item);

        if (userItem == null) {
            throw new IllegalArgumentException("구매가 필요합니다");
        }
        if (userItem.getEquipped()) {
            throw new IllegalArgumentException("이미 착용된 아이템입니다");
        }

        userItem.updateEquipped(true);

        return userItemRepository.findAllByUserAndEquipped(user, true).stream()
                .map(equippedUserItem -> EquippedItemResponseDto.of(
                        equippedUserItem.getItem().getId(),
                        equippedUserItem.getItem().getName(),
                        equippedUserItem.getItem().getItemCategory()
                ))
                .toList();
    }

    public List<EquippedItemResponseDto> unequippedItem(Long itemId, Long userId) {
        User user = userService.findById(userId);
        Item item = itemRepository.findById(itemId).orElseThrow();
        UserItem userItem = userItemRepository.findByUserAndItem(user, item);

        if (userItem == null) {
            throw new IllegalArgumentException("구매가 필요합니다");
        }
        if (!userItem.getEquipped()) {
            throw new IllegalArgumentException("이미 미착용된 아이템입니다");
        }

        userItem.updateEquipped(false);

        return userItemRepository.findAllByUserAndEquipped(user, true).stream()
                .map(equippedUserItem -> EquippedItemResponseDto.of(
                        equippedUserItem.getItem().getId(),
                        equippedUserItem.getItem().getName(),
                        equippedUserItem.getItem().getItemCategory()
                ))
                .toList();
    }

    public List<EquippedItemResponseDto> findEquippedItem(Long userId) {
        User user = userService.findById(userId);

        return userItemRepository.findAllByUserAndEquipped(user, true).stream()
                .map(equippedUserItem -> EquippedItemResponseDto.of(
                        equippedUserItem.getItem().getId(),
                        equippedUserItem.getItem().getName(),
                        equippedUserItem.getItem().getItemCategory()
                ))
                .toList();
    }
}
