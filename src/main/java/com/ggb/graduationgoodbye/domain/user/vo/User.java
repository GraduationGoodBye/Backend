package com.ggb.graduationgoodbye.domain.user.vo;

import com.ggb.graduationgoodbye.domain.user._enum.Role;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String profile;
    private String name;
    private String nickname;
    private String address;
    private String phone;
    private String gender;
    private Role role;

    @Builder(builderMethodName = "builder")
    public User(
            String email,
            String profile,
            String name,
            String nickname,
            String address,
            String phone,
            String gender
    ) {
        this.email = email;
        this.profile = profile;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.role = Role.USER;
    }
}
