package com.alom.dorundorunbe.domain.image.controller;

import com.alom.dorundorunbe.domain.image.domain.ImageCategory;
import com.alom.dorundorunbe.domain.image.dto.ImageResponseDto;
import com.alom.dorundorunbe.domain.image.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class S3ImageController {

    private final S3ImageService s3ImageService;

    @GetMapping("/")
    @Operation(summary = "전체 이미지 조회", description = "저장된 모든 이미지 URL을 반환합니다")
    public ResponseEntity<ImageResponseDto> getAllImages() {
        return null;
    }

    @GetMapping("/{imageCategory}")
    @Operation(summary = "카테고리별 이미지 조회", description = "아이템, 업적 등 특정 카테고리의 모든 이미지 URL을 반환합니다")
    public ResponseEntity<ImageResponseDto> getAllImagesByCategory(@PathVariable("imageCategory") ImageCategory imageCategory) {
        return null;
    }
}
