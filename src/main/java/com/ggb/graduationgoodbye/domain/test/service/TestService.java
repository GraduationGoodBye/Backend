package com.ggb.graduationgoodbye.domain.test.service;

import com.ggb.graduationgoodbye.utils.Base64Util;
import com.ggb.graduationgoodbye.utils.S3Util;
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
    private final S3Util s3Util;
    private final Base64Util base64Util;

    public String uploadImageTest(MultipartFile image){
        return s3Util.upload(image);
    }

    public String encode(String data){
        return base64Util.encode(data);
    }


    public String decode(String encodedData){
        return base64Util.decode(encodedData);
    }
}
