package com.ggb.graduationgoodbye.domain.test.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImageTest(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename(); // 클라이언트가 전송한 파일 이름
        String extension = StringUtils.getFilenameExtension(originalFilename); // 파일 확장자
        String uuidImageName = UUID.randomUUID() + "." + extension; // 파일 이름 중복 방지
        // S3에 파일 업로드
        S3Resource s3Resource = s3Template.upload(bucketName,uuidImageName,image.getInputStream(),
                ObjectMetadata.builder().contentType(image.getContentType()).build());
        // 업로드 된 이미지 url 반환
        return s3Resource.getURL().toString();
    }
}
