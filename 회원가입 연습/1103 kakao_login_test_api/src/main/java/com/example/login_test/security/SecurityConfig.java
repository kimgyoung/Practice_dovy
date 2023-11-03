package com.example.login_test.security;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    public class CustomSecurityFilterManger extends AbstractHttpConfigurer<CustomSecurityFilterManger, HttpSecurity>{

        public void configure(HttpSecurity httpSecurity) throws Exception {

            AuthenticationManager authenticationManager = httpSecurity.getSharedObject
                    (AuthenticationManager.class);

            httpSecurity.addFilter(new JwtAuthenticationFilter(authenticationManager));

            super.configure(httpSecurity);
        }
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();

        httpSecurity.headers().frameOptions().sameOrigin();

        httpSecurity.cors().configurationSource(corsConfigurationSource());

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.apply(new CustomSecurityFilterManger());

        httpSecurity.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다." + authException.getMessage());
            //FilterResponseUtils 인증X

        });

        httpSecurity.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
           log.warn("권한이 없는 사용자가 자원에 접근하려 합니다.: " + accessDeniedException.getMessage());
           //FilterResponseUtils 권한X
        });

        httpSecurity.authorizeRequests(
                authorize -> authorize.antMatchers("/carts/**", "/options/**", "/orders/**").authenticated()
                        .antMatchers("/admin/**")
                        .access("hasRole('ADMIN')")
                        .anyRequest().permitAll()
        );
        return httpSecurity.build();
    }

    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfigurationSource = new CorsConfiguration();
        corsConfigurationSource.addAllowedHeader("*");
        corsConfigurationSource.addAllowedMethod("*");
        corsConfigurationSource.addAllowedOriginPattern("*");
        corsConfigurationSource.setAllowCredentials(true);
        corsConfigurationSource.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfigurationSource);

        return urlBasedCorsConfigurationSource;
    }
}
