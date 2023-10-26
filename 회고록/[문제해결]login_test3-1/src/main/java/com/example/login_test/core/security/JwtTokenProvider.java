package com.example.login_test.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.login_test.user.StringArrayConverter;
import com.example.login_test.user.User;

import java.security.SignatureException;
import java.util.Date;

public class JwtTokenProvider {

    // 초 단위 60번 60분 == 1시간
    // 토큰 기본 설정 1시간으로 잡아 놓음
    private static final Long EXP = 1000L * 60 * 60;

    // ** Authorization 헤더에 필요한 데이터
    public static final String TOKEN_PREFIX = "Bearer ";

    // ** 헤더
    public static final String HEADER = "Authorization";

    // ** 암호화 및 복호화에 필요한 키
    private static final String SECRET = "SECRET_KEY";

    // ** JWT 토큰 생성
    public static String create(User user){

        // ** StringArrayConverter 객체 생성
        StringArrayConverter stringArrayConverter = new StringArrayConverter();

        String roles = stringArrayConverter.convertToDatabaseColumn(
                user.getRoles()
        );

        // ** User 의 권한 정보를 String 으로 변경
        String jwt = JWT.create()
                .withSubject(user.getEmail()) // ** 토큰의 대상 정보 생성
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP)) // ** 시간 설정
                .withClaim("id", user.getId()) // ** id 설정
                .withClaim("roles", roles) // ** 권한 정보 생성
                .sign(Algorithm.HMAC512(SECRET)); //** jwt 생성 알고리즘 실행

        return TOKEN_PREFIX + jwt;
    }

    // ** 토큰 검증을 시작
    // 이 함수를 실행했을 때 넘어오는 값 (String jwt)
    // Algorithm.HMAC512(SECRET) 기준으로 jwt를 검증해라
    // Key는 SECRET
    // 그리고 반환

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(jwt);
        return decodedJWT;
    }

}
