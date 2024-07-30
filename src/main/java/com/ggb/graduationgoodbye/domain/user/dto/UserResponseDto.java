package com.ggb.graduationgoodbye.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String profile;
    private String name;
    private String nickname;
    private String address;
    private String phone;
    private String gender;
    private String role;
}
