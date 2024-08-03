package com.ggb.graduationgoodbye.domain.auth.dto;

import com.ggb.graduationgoodbye.domain.auth.exception.InvalidRegistrationIdException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserInfo {

    private String email;
    private String profile;
    private String name;

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> oAuth2UserAttributes) {

        return switch (registrationId) {
            case "google" -> ofGoogle(oAuth2UserAttributes);
            // 새로운 리소스 서버 추가 ...
            default -> throw new InvalidRegistrationIdException();
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name(attributes.get("name").toString())
                .email(attributes.get("email").toString())
                .profile(attributes.get("picture").toString())
                .build();
    }

//    public User dtoToVo() {
//        return User.builder()
//                .email(email)
//                .profile(profile)
//                .name(name)
//                .build();
//    }
}
