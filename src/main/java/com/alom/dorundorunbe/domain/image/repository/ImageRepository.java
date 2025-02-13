package com.alom.dorundorunbe.domain.image.repository;

import com.alom.dorundorunbe.domain.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Item, Long> {
}
