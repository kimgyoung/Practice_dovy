package com.example.login_test.kakao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Token {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private long refreshTokenExpiresIn;

    private String userId;
}