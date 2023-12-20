package com.example.snsProject.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class CustomOAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        String username = getUsernameFromAuthentication(authentication);
        // 권한을 폼 로그인과 동일하게 부여
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // OAuth2 사용자의 정보를 UserDetails에 넣어주기
        UserDetails userDetails = createUserDetails(authentication);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
        // OAuth2 로그인 성공 후 리다이렉트할 URL
        String targetUrl = "/view/home";

        // 리다이렉트
        try {
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            response.setHeader("Location", targetUrl);
            response.setStatus(HttpServletResponse.SC_FOUND);
        }
    }

    private String getUsernameFromAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername();
        } else if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            return oAuth2User.getAttribute("username");
        } else {
            return "N/A";
        }
    }

    private UserDetails createUserDetails(Authentication authentication) {
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // 여기에서 OAuth2User의 정보를 이용하여 UserDetails를 생성
            return createCustomUserDetails(oAuth2User);
        } else {
            // 폼 로그인과 동일한 권한 부여를 위해 authorities를 전달
            return User.builder()
                    .username("N/A")
                    .password("")
                    .authorities(authentication.getAuthorities())
                    .build();
        }
    }

    private UserDetails createCustomUserDetails(OAuth2User oAuth2User) {
        // 여기에서 필요한 정보를 추출하여 UserDetails를 생성
        return User.withUsername(oAuth2User.getAttribute("username"))
                .authorities(oAuth2User.getAuthorities())
                .password("")  // 비밀번호 필드는 OAuth2에서 가져오지 않으므로 빈 문자열을 설정하거나 적절한 값을 선택
                .build();
    }
}