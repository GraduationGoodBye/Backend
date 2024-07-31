package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.dto.OAuth2UserInfo;
import com.ggb.graduationgoodbye.domain.auth.dto.PrincipalDetails;
import com.ggb.graduationgoodbye.domain.auth.exception.NotJoinedUserException;
import com.ggb.graduationgoodbye.domain.user.repository.UserRepository;
import com.ggb.graduationgoodbye.domain.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        Map<String,Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        if(!isJoinedUser(oAuth2UserAttributes)){
            String accessToken = userRequest.getAccessToken().getTokenValue();
            throw new NotJoinedUserException(accessToken);
        }

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);

        User user = getOrSave(oAuth2UserInfo);

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new PrincipalDetails(user, oAuth2UserAttributes, userNameAttributeName);
    }

    private User getOrSave(OAuth2UserInfo oAuth2UserInfo) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(oAuth2UserInfo.getEmail()));
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        else{
            User user = oAuth2UserInfo.dtoToVo();
            userRepository.save(user);
            return user;
        }
    }

    private boolean isJoinedUser(Map<String, Object> oAuth2UserAttributes) {
        String email = oAuth2UserAttributes.get("email").toString();

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));

        return optionalUser.isPresent();
    }
}
