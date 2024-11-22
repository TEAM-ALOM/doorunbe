package com.alom.dorundorunbe.domain.item.repository;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByItemCategory(ItemCategory itemCategory);
}
