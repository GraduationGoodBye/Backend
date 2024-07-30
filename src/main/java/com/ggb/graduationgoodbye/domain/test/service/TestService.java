package com.ggb.graduationgoodbye.domain.test.service;

import com.ggb.graduationgoodbye.domain.s3.utils.Base64Util;
import com.ggb.graduationgoodbye.domain.s3.utils.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
