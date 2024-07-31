package com.ggb.graduationgoodbye.domain.user.service.impl;

import com.ggb.graduationgoodbye.domain.user.repository.UserRepository;
import com.ggb.graduationgoodbye.domain.user.service.UserService;
import com.ggb.graduationgoodbye.domain.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Long join(User user){
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public boolean existsByEmail(String email){
        return userRepository.findByEmail(email) != null;
    }
}
