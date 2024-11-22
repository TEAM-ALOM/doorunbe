package com.alom.dorundorunbe.domain.item.repository;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.UserItem;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    Boolean existsByUserAndItem(User user, Item item);
    UserItem findByUserAndItem(User user, Item item);
    List<UserItem> findAllByUserAndEquipped(User user, Boolean equipped);
}
