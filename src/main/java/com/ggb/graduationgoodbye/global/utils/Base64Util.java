package com.ggb.graduationgoodbye.global.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class Base64Util {
    public String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public String decode(String encodedData) {
        return new String(Base64.getDecoder().decode(encodedData));
    }
}
