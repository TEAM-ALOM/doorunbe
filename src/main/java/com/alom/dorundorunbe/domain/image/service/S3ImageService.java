package com.alom.dorundorunbe.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ImageService {

    private final S3Client s3Client;

    public String upload(MultipartFile file, String bucket, String category) {
        UUID uuid = UUID.randomUUID();

        String date = LocalDate.now().toString();

        String route = category + "/" + date + "/" + uuid;

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(route)
                .contentType("image/png")
                .build();
        s3Client.putObject(objectRequest, RequestBody.fromBytes(getBytes(file)));

        return route;
    }

    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
