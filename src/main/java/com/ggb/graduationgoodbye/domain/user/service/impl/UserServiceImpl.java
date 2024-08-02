package com.ggb.graduationgoodbye.domain.user.service.impl;

import com.ggb.graduationgoodbye.domain.auth.dto.PrincipalDetails;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public Token join(UserJoinRequest request) {
        // NOTE : 확장성 고려, OpenFeign 또는 추상화 도입 고민
        String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + request.accessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(userInfoEndpointUri, HttpMethod.GET, entity, String.class);

        log.info("Google Response >> {}", response.getBody());

        User googleUser = new Gson().fromJson(response.getBody(), User.class);

        User user = User.builder()
                .email(googleUser.getEmail())
                .profile(googleUser.getProfile())
                .nickname(request.nickname())
                .address(request.address())
                .phone(request.phone())
                .gender(request.gender())
                .build();
        userRepository.save(user);
        
        return tokenService.getToken(makeAuthentication(user));
    }

    // NOTE : 추가 수정 필요 - 메서드 위치, 구현 방식
    private Authentication makeAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(
                user.getId().toString(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
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
