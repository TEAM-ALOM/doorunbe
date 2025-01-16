package com.alom.dorundorunbe.domain.item.service;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.domain.UserItem;
import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.item.dto.ItemResponseDto;
import com.alom.dorundorunbe.domain.item.repository.ItemRepository;
import com.alom.dorundorunbe.domain.item.repository.UserItemRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserItemRepository userItemRepository;

    @Mock
    private UserService userService;

    private User mockUser;
    private Item mockItem;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .id(1L)
                .nickname("test")
                .email("test@test.com")
                .cash(1000L)
                .build();

        mockItem = Item.builder()
                .id(1L)
                .name("testItem")
                .itemCategory(ItemCategory.CLOTHES)
                .cost(100L)
                .build();
    }

    @Test
    @DisplayName("카테고리 별 아이템 목록 조회 성공")
    void findItemByCategory_Success() {
        ItemCategory itemCategory = ItemCategory.CLOTHES;
        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findAllByItemCategory(itemCategory)).thenReturn(List.of(mockItem));
        when(userItemRepository.existsByUserAndItem(mockUser, mockItem)).thenReturn(false);

        List<ItemResponseDto> itemResponseDtoList = itemService.findItemByCategory(itemCategory, 1L);

        assertNotNull(itemResponseDtoList);
        assertEquals(1, itemResponseDtoList.size());
        assertEquals(mockItem.getId(), itemResponseDtoList.get(0).id());
        assertFalse(itemResponseDtoList.get(0).owned());
    }

    @Test
    @DisplayName("아이템 구매 성공")
    void purchaseItem_Success() {
        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));
        when(userItemRepository.existsByUserAndItem(mockUser, mockItem)).thenReturn(false);

        itemService.purchaseItem(1L, 1L);

        verify(userItemRepository, times(1)).save(any(UserItem.class));
        assertEquals(900, mockUser.getCash());
    }

    @Test
    @DisplayName("아이템 구매 실패: 이미 소유한 경우")
    void purchaseItem_AlreadyOwned() {
        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));
        when(userItemRepository.existsByUserAndItem(mockUser, mockItem)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> itemService.purchaseItem(1L, 1L));

        assertEquals("이미 소유한 아이템입니다", exception.getMessage());
    }

    @Test
    @DisplayName("아이템 구매 실패: 잔액 부족")
    void purchaseItem_NotEnoughMoney() {
        mockUser.updateCash(10L);
        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));
        when(userItemRepository.existsByUserAndItem(mockUser, mockItem)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> itemService.purchaseItem(1L, 1L));

        assertEquals("잔액이 부족합니다", exception.getMessage());
    }

    @Test
    @DisplayName("아이템 착용 성공")
    void equippedItem_Success() {
        UserItem userItem_false = mock(UserItem.class);
        UserItem userItem_true = UserItem.builder()
                .user(mockUser)
                .item(mockItem)
                .equipped(true)
                .build();

        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));
        when(userItemRepository.findByUserAndItem(mockUser, mockItem))
                .thenReturn(userItem_false);
        when(userItemRepository.findAllByUserAndEquipped(mockUser, true))
                .thenReturn(List.of(userItem_true));

        List<EquippedItemResponseDto> equippedItemList = itemService.equippedItem(1L, 1L);

        assertNotNull(equippedItemList);
        verify(userItem_false, times(1)).updateEquipped(true);
        assertEquals(1, equippedItemList.size());
        assertEquals(equippedItemList.get(0).name(), "testItem");
    }

    @Test
    @DisplayName("아이템 착용 실패: 구매하지 않은 아이템")
    void equippedItem_PurchaseRequired() {
        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->itemService.equippedItem(1L, 1L));

        assertEquals("구매가 필요합니다", exception.getMessage());
    }

    @Test
    @DisplayName("아이템 착용 실패: 이미 착용한 아이템")
    void equippedItem_AlreadyEquipped() {
        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));
        when(userItemRepository.findByUserAndItem(mockUser, mockItem))
                .thenReturn(UserItem.builder()
                        .user(mockUser)
                        .item(mockItem)
                        .equipped(true)
                        .build());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> itemService.equippedItem(1L, 1L));

        assertEquals("이미 착용된 아이템입니다", exception.getMessage());
    }

    @Test
    @DisplayName("아이템 착용 해제 성공")
    void unequippedItem_Success() {
        UserItem mockUserItem = mock(UserItem.class);

        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));
        when(userItemRepository.findByUserAndItem(mockUser, mockItem))
                .thenReturn(mockUserItem);
        when(userItemRepository.findAllByUserAndEquipped(mockUser, true))
                .thenReturn(List.of());
        when(mockUserItem.getEquipped()).thenReturn(true);

        List<EquippedItemResponseDto> equippedItemList = itemService.unequippedItem(1L, 1L);

        assertNotNull(equippedItemList);
        assertEquals(0, equippedItemList.size());
        verify(mockUserItem, times(1)).getEquipped();
    }

    @Test
    @DisplayName("아이템 착용 해제 실패: 착용하지 않은 아이템")
    void unequippedItem_AlreadyUnequipped() {
        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));
        when(userItemRepository.findByUserAndItem(mockUser, mockItem))
                .thenReturn(UserItem.builder()
                        .user(mockUser)
                        .item(mockItem)
                        .equipped(false)
                        .build());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> itemService.unequippedItem(1L, 1L));

        assertEquals("이미 미착용된 아이템입니다", exception.getMessage());
    }

    @Test
    @DisplayName("아이템 착용 해제 실패: 구매하지 않은 아이템")
    void unequippedItem_PurchaseRequired() {
        when(userService.findById(1L)).thenReturn(mockUser);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->itemService.unequippedItem(1L, 1L));

        assertEquals("구매가 필요합니다", exception.getMessage());
    }

    @Test
    @DisplayName("착용한 아이템 목록 조회 성공")
    void findEquippedItem() {
        UserItem equippedUserItem = UserItem.builder()
                .user(mockUser)
                .item(mockItem)
                .equipped(true)
                .build();

        when(userService.findById(1L)).thenReturn(mockUser);
        when(userItemRepository.findAllByUserAndEquipped(mockUser, true)).thenReturn(List.of(equippedUserItem));

        List<EquippedItemResponseDto> dtoList = itemService.findEquippedItemList(1L);

        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());
        assertEquals(mockItem.getName(), dtoList.get(0).name());
    }
}