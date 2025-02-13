package com.alom.dorundorunbe.domain.item.service;

import com.alom.dorundorunbe.domain.image.domain.Image;
import com.alom.dorundorunbe.domain.image.service.ImageService;
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
import com.alom.dorundorunbe.global.exception.BusinessException;
import com.alom.dorundorunbe.global.exception.ErrorCode;
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
    private final ImageService imageService;

    public void createItem(ItemRequestDto dto) {
        Image image = imageService.findById(dto.imageId());

        Item item = Item.builder()
                .name(dto.name())
                .itemCategory(dto.itemCategory())
                .image(image)
                .cost(dto.cost())
                .build();

        itemRepository.save(item);
    }

    public void updateItemImage(Long itemId, ItemRequestDto dto) {
        Item item = findItemById(itemId);
        Image image = imageService.findById(dto.imageId());

        item.update(dto.name(), dto.itemCategory(), image, dto.cost());
    }

    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        itemRepository.delete(item);
    }

    public Item findItemById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
    }

    public List<ItemResponseDto> findItemByCategory(ItemCategory itemCategory, Long userId) {

        User user = userService.findById(userId);
        List<Item> itemList = itemRepository.findAllByItemCategory(itemCategory);

        return itemList.stream()
                .map(item -> ItemResponseDto.of(
                        item.getId(),
                        item.getName(),
                        item.getImage().getId(),
                        item.getCost(),
                        userItemRepository.existsByUserAndItem(user, item)
                ))
                .sorted(Comparator.comparing(ItemResponseDto::owned).reversed())
                .toList();
    }

    public void purchaseItem(Long itemId, Long userId) {
        User user = userService.findById(userId);
        Item item = findItemById(itemId);

        if (userItemRepository.existsByUserAndItem(user, item)) {
            throw new BusinessException(ErrorCode.ITEM_ALREADY_OWNED);
        }
        if (user.getCash()<item.getCost()) {
            throw new BusinessException(ErrorCode.REJECT_ITEM_PAYMENT);
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
        Item item = findItemById(itemId);
        UserItem userItem = userItemRepository.findByUserAndItem(user, item);

        if (userItem == null) {
            throw new BusinessException(ErrorCode.PAYMENT_REQUIRED);
        }
        if (userItem.getEquipped()) {
            throw new BusinessException(ErrorCode.ITEM_ALREADY_EQUIPPED);
        }

        userItem.updateEquipped(true);

        return findEquippedItemList(user);
    }

    public List<EquippedItemResponseDto> unequippedItem(Long itemId, Long userId) {
        User user = userService.findById(userId);
        Item item = itemRepository.findById(itemId).orElseThrow();
        UserItem userItem = userItemRepository.findByUserAndItem(user, item);

        if (userItem == null) {
            throw new BusinessException(ErrorCode.PAYMENT_REQUIRED);
        }
        if (!userItem.getEquipped()) {
            throw new BusinessException(ErrorCode.ITEM_ALREADY_UNEQUIPPED);
        }

        userItem.updateEquipped(false);

        return findEquippedItemList(user);
    }

    public List<EquippedItemResponseDto> findEquippedItemList(Long userId) {
        User user = userService.findById(userId);
        return findEquippedItemList(user);
    }

    private List<EquippedItemResponseDto> findEquippedItemList(User user) {
        return userItemRepository.findAllByUserAndEquipped(user, true).stream()
                .map(equippedUserItem -> EquippedItemResponseDto.of(
                        equippedUserItem.getItem().getId(),
                        equippedUserItem.getItem().getName(),
                        equippedUserItem.getItem().getItemCategory(),
                        equippedUserItem.getItem().getImage().getId()
                ))
                .toList();
    }
}
