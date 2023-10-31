package com.example.login_test.kakao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class KakaoData {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private String refresh_token;
    private String refresh_token_expires_in;
}

/*
응답: 성공
        HTTP/1.1 200 OK
        Content-Type: application/json;charset=UTF-8
        {
        "token_type":"bearer",
        "access_token":"${ACCESS_TOKEN}",
        "expires_in":43199,
        "refresh_token":"${REFRESH_TOKEN}",
        "refresh_token_expires_in":5184000,
        "scope":"account_email profile"
        }

 */