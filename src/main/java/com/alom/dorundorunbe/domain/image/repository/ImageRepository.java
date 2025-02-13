package com.alom.dorundorunbe.domain.image.repository;

import com.alom.dorundorunbe.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
