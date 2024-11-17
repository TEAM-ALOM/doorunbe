package com.alom.dorundorunbe.domain.item.service;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
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
}
