package com.example.login_test.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration // ** 현재 클래스를 (설정 클래스로) 설정
public class SecurityConfig {

    // 인코더 기본으로 생성
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
        /* BCrypt : 기본으로 사용. 가장 많이 사용 되는 알고리즘
        * SCrypt : 개발자가 직접 필요에 따라 변경 가능.
        * Argon2
        * PBKDF2
        * MD5 - 지금은 쓰지 않는 것
        * SHA-1, SHA-256 등 - 지금은 쓰지 않는 것
        */
    }

    // ** 스프링 빈 등록
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // ** Security 기본 인증을 비활성화
        httpSecurity.httpBasic().disable();

        return httpSecurity.build();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity>{

        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception{

            AuthenticationManager authenticationManager = httpSecurity.getSharedObject(
                    AuthenticationManager.class
            );

            httpSecurity.addFilter(new JwtAuthenticationFilter(authenticationManager));

            super.configure(httpSecurity);
        }

    }
}
