package com.alom.dorundorunbe.domain.auth.service;

import com.alom.dorundorunbe.domain.auth.dto.AuthUserDto;
import com.alom.dorundorunbe.domain.auth.dto.PrincipalUserDetails;
import com.alom.dorundorunbe.domain.auth.provider.OAuth2AttributeProvider;
import com.alom.dorundorunbe.domain.user.domain.Gender;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrincipalUserDetailsService extends DefaultOAuth2UserService implements UserDetailsService {

    private final OAuth2AttributeProvider oAuth2AttributeProvider;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // userRequest: (로그인 요청 -> 인가 코드 -> 토큰 -> 사용자 정보)의 프로세스에서 최종적으로 반환된 사용자 정보
        // 사용자 정보 추출 -> email로 회원 존재 확인 -> 회원을 등록하거나 업데이트 -> 회원을 OAuth2User로 감싸서 반환

        // 사용자 정보 (registrationId + attribute + etc)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        AuthUserDto authUserDto = oAuth2AttributeProvider
                .convertUserAttribute(userRequest.getClientRegistration().getRegistrationId(), oAuth2User);

        User user = userService.findByEmail(authUserDto.email()).orElse(registerUser(authUserDto));

        return new PrincipalUserDetails(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username)
                .orElseThrow();

        return new PrincipalUserDetails(user);
    }

    private User registerUser(AuthUserDto authUserDto) {
        // age과 gender의 경우 oauth 로그인으로 바로 가져오려면 사업자 정보 등록이 필요해서 이 부분은 논의 필요

        return User.builder()
                .email(authUserDto.email())
                .nickname(authUserDto.nickname())
                .age(0)
                .gender(Gender.MALE)
                .cash(0)
                .build();
    }
}
