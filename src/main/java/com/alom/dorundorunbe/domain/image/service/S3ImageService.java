package com.alom.dorundorunbe.domain.image.service;

import com.alom.dorundorunbe.domain.image.domain.ImageCategory;
import com.alom.dorundorunbe.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3ImageService {

    private final ImageRepository imageRepository;
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public List<String> getAllImages() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        return response.contents().stream()
                .map(s3Object -> getImageUrl(s3Object.key()))
                .toList();
    }

    public List<String> getAllImagesByCategory(ImageCategory category) {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(category + "/")
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        return response.contents().stream()
                .map(s3Object -> getImageUrl(s3Object.key()))
                .collect(Collectors.toList());
    }

    private String getImageUrl(String key) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
    
}
