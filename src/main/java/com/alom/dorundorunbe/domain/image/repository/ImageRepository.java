package com.alom.dorundorunbe.domain.image.repository;

import com.alom.dorundorunbe.domain.image.domain.Image;
import com.alom.dorundorunbe.domain.image.domain.ImageCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByCategory(ImageCategory category);
}
