package com.alom.dorundorunbe.domain.item.repository;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.UserItem;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    Boolean findByUserAndItem(User user, Item item);
}
