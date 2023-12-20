package com.example.snsProject.service;

import com.example.snsProject.model.DAO.MemberRepository;
import com.example.snsProject.model.DTO.Member;
import com.example.snsProject.model.DTO.OAuthAttributes;
import com.example.snsProject.model.DTO.SocialUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private Member member;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member user = saveOrUpdate(attributes);
        OAuth2User userDetails = loadUserById(user.getEmail());

        return  userDetails;
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        String email = "";
        Map<String, Object> attributesMap = (Map<String, Object>)attributes.getAttributes().get("properties");
        if (attributes.getEmail() == null || attributes.getEmail().isEmpty()) {
            email = attributes.getName() + "@kakao.com";
        } else {
            email = attributes.getEmail();
        }
        Optional<SocialUsers> userOptional = memberRepository.findBySocialEmail(email);
        SocialUsers user = userOptional
                .map(entity -> entity.update(attributes.getName(), attributes.getProvider()))
                .orElse(attributes.toEntity());
        user.kakaoUpdate(email);
        memberRepository.saveOrUpdate(user);
        if (email.contains("@kakao.com")) {
            memberService.updateProfile(email, (String) attributesMap.get("profile_image"));
        }

        Optional<Member> findOne = memberService.findOne(user.getEmail());
        member = findOne.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + attributes.getEmail()));
        return member;
    }

    public OAuth2User loadUserById(String email) {
        Optional<Member> findOne = memberService.findOne(email);
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", member.getId().toString());
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "username" // Assuming "email" is the attribute key for the username
        );
    }

}
