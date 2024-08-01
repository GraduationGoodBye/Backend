package com.ggb.graduationgoodbye.domain.user.dto;

import com.ggb.graduationgoodbye.domain.user.vo.User;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private String email;
    private String nickname;
    private String address;
    private String phone;
    private String gender;

    public User dtoToVo() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .address(address)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
