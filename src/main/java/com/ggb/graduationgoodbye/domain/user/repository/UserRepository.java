package com.ggb.graduationgoodbye.domain.user.repository;

import com.ggb.graduationgoodbye.domain.user.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
