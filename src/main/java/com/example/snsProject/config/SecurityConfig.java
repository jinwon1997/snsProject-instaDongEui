package com.example.snsProject.config;

import com.example.snsProject.handler.CustomOAuth2LoginFailedHandler;
import com.example.snsProject.handler.CustomOAuth2LoginSuccessHandler;
import com.example.snsProject.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2LoginSuccessHandler customOAuth2LoginSuccessHandler;
    private final CustomOAuth2LoginFailedHandler customOAuth2LoginFailedHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // POST방식 허용
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/api/user/**","/","/view/register", "/method/registUser", "/css/**", "/img/**", "/js/**").permitAll() // 이 URI는 누구든 접근가능
                                .requestMatchers("/api/admin/**").hasRole("ADMIN") // ADMIN role만 접근 가능
                                .anyRequest().authenticated() // 어떤 URI로 접근하던 인증이 필요함
                )
                .formLogin(
                        login -> login
                                .loginPage("/view/loginTest").permitAll()
                                .defaultSuccessUrl("/view/home", true)
                                .loginProcessingUrl("/loginProc") // 이 URI 호출시 스프링 시큐리티로 폼 정보를 제출 / form의 action
                                .usernameParameter("id") // 폼 input name값: default - username
                                .passwordParameter("password") // 폼 input name값: default - password
//                                .successHandler(핸들러이름()) // 로그인 성공을 다룰 핸들러
//                                .failureHandler(핸들러이름()) // 로그인 실패를 다룰 핸들러
                                .permitAll()
                )
                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(customOAuth2UserService) // CustomOAuth2UserService를 사용하여 OAuth2User를 처리

                                )
                                .permitAll()
                                .successHandler(customOAuth2LoginSuccessHandler)
                                .failureHandler(customOAuth2LoginFailedHandler)

                )

                .logout(Customizer.withDefaults()); // 기본설정 (/logout로 인증 해제)

        return http.build();
    }
}
