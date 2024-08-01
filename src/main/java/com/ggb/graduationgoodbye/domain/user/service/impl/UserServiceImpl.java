package com.ggb.graduationgoodbye.domain.user.service.impl;

import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.auth.vo.Token;
import com.ggb.graduationgoodbye.domain.user.dto.UserJoinRequest;
import com.ggb.graduationgoodbye.domain.user.repository.UserRepository;
import com.ggb.graduationgoodbye.domain.user.service.UserService;
import com.ggb.graduationgoodbye.domain.user.vo.User;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public Token join(UserJoinRequest request) {
        String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + request.accessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(userInfoEndpointUri, HttpMethod.GET, entity, String.class);

        log.info("Google Response >> {}", response.getBody());

        User googleUser = new Gson().fromJson(response.getBody(), User.class);

        User user = User.builder()
                .email(googleUser.getEmail())
                .nickname(request.nickname())
                .address(request.address())
                .phone(request.phone())
                .gender(request.gender())
                .build();
        userRepository.save(user);

        // TODO: 토큰 발급 추
        return Token.of("", "");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id));
    }
}
