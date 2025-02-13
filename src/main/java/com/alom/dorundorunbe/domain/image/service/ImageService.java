package com.alom.dorundorunbe.domain.image.service;

import com.alom.dorundorunbe.domain.image.domain.Image;
import com.alom.dorundorunbe.domain.image.domain.ImageCategory;
import com.alom.dorundorunbe.domain.image.dto.ImageResponseDto;
import com.alom.dorundorunbe.domain.image.repository.ImageRepository;
import com.alom.dorundorunbe.global.config.AwsS3Credentials;
import com.alom.dorundorunbe.global.exception.BusinessException;
import com.alom.dorundorunbe.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final AwsS3Credentials awsS3Credentials;
    private final S3ImageService s3ImageService;

    @Transactional
    public ImageResponseDto save(MultipartFile file, String category) {

        String bucket = awsS3Credentials.getBucket();
        String route = s3ImageService.upload(file, bucket, category);

        Image image = Image.builder()
                .url(awsS3Credentials.getImageUrl() + route + ".png")
                .category(ImageCategory.valueOf(category))
                .build();

        return ImageResponseDto.from(imageRepository.save(image));
    }

    public List<ImageResponseDto> getAllImages() {
        return imageRepository.findAll().stream()
                .map(ImageResponseDto::from)
                .toList();
    }

    public List<ImageResponseDto> getAllImagesByCategory(ImageCategory category) {
        return imageRepository.findAllByCategory(category).stream()
                .map(ImageResponseDto::from)
                .toList();
    }

    public Image findById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.IMAGE_NOT_FOUND));
    }
}
