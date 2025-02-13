package com.alom.dorundorunbe.domain.image.controller;

import com.alom.dorundorunbe.domain.image.domain.ImageCategory;
import com.alom.dorundorunbe.domain.image.dto.ImageResponseDto;
import com.alom.dorundorunbe.domain.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    @Operation(summary = "이미지 업로드", description = "이미지를 저장합니다")
    public ResponseEntity<ImageResponseDto> upload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("category") String category) {
        return ResponseEntity.ok(imageService.save(file, category));
    }

    @GetMapping("/")
    @Operation(summary = "전체 이미지 조회", description = "저장된 모든 이미지 URL을 반환합니다")
    public ResponseEntity<List<ImageResponseDto>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{imageCategory}")
    @Operation(summary = "카테고리별 이미지 조회", description = "아이템, 업적 등 특정 카테고리의 모든 이미지 URL을 반환합니다")
    public ResponseEntity<List<ImageResponseDto>> getAllImagesByCategory(@PathVariable("imageCategory") ImageCategory category) {
        return ResponseEntity.ok(imageService.getAllImagesByCategory(category));
    }
}
