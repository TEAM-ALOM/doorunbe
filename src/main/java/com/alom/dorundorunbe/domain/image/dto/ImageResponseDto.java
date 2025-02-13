package com.alom.dorundorunbe.domain.image.dto;

import com.alom.dorundorunbe.domain.image.domain.Image;
import com.alom.dorundorunbe.domain.image.domain.ImageCategory;

public record ImageResponseDto(
        Long id,
        String url,
        ImageCategory category
) {
    public static ImageResponseDto from(Image image) {
        return new ImageResponseDto(image.getId(), image.getUrl(), image.getCategory());
    }
}
