package com.ggb.graduationgoodbye.domain.user.service;

import com.ggb.graduationgoodbye.domain.auth.vo.Token;
import com.ggb.graduationgoodbye.domain.user.dto.UserJoinRequest;
import com.ggb.graduationgoodbye.domain.user.vo.User;

import java.util.Optional;

public interface UserService {
    Token join(UserJoinRequest request);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findById(Long id);
}
