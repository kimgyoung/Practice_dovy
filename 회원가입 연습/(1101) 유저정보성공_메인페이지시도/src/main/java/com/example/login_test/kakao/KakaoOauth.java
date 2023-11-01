package com.example.login_test.kakao;

import lombok.Getter;

@Getter
public class KakaoOauth {

    private String token_type;
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String refresh_token_expires_in;

}
