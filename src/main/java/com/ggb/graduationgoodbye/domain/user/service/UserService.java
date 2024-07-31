package com.ggb.graduationgoodbye.domain.user.service;

import com.ggb.graduationgoodbye.domain.user.vo.User;

import java.util.Optional;

public interface UserService {
    Long join(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
