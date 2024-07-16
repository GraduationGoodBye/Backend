package com.ggb.graduationgoodbye.utils;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Util {
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public String upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename(); // 전송한 파일명
        String extension = StringUtils.getFilenameExtension(fileName);
        String key = UUID.randomUUID() + "." + extension;
        S3Resource s3Resource = s3Template.upload(bucketName, key, file.getInputStream(),
                ObjectMetadata.builder().contentType(file.getContentType()).build());
        return s3Resource.getURL().toString();
    }
}
