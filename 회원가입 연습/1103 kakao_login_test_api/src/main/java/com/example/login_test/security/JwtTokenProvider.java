package com.example.login_test.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.login_test.user.StringArrayConverter;
import com.example.login_test.user.User;

import java.util.Date;

public class JwtTokenProvider {

    private static final Long EXP = 1000L * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    private static final String SECRET = "SECRET_KEY";

    public static String create(User user){

        StringArrayConverter stringArrayConverter = new StringArrayConverter();
        String roles = stringArrayConverter.convertToDatabaseColumn(user.getRoles());

        String jwt = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId())
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC512(SECRET));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        return decodedJWT;
    }
}
