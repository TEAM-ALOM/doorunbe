package com.alom.dorundorunbe.domain.image.dto;

import com.alom.dorundorunbe.domain.image.domain.ImageCategory;

public record ImageResponseDto(
        Long id,
        String fileName,
        String url,
        ImageCategory category
) {
    public static ImageResponseDto of(Long imageId, String fileName, String url, ImageCategory category) {
        return new ImageResponseDto(imageId, fileName, url, category);
    }
}
