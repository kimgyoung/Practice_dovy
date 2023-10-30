package com.example.login_test.core.kakaoSecurity;

import com.example.login_test.user.User;

public class KaKaoTokenProvider {

    // ** JWT 토큰의 만료 시간을 1시간으로 설정.
    private static final Long EXP = 1000L * 60 * 60;

    // ** 인증 헤더에 사용될 토큰의 접두어 ("Bearer ")
    public static final String TOKEN_PREFIX = "Bearer ";

    // ** 인증 헤더의 이름을 "Authorization"으로 설정.
    public static final String HEADER = "Authorization";

    // ** 토큰의 서명을 생성하고 검증할 때 사용하는 비밀 키
    private static final String SECRET = "SECRET_KEY";

    public static String create(User user){


    }
}
