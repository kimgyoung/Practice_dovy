package com.example.login_test.kakao;

import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// (Get)인가 코드 받기
// 쿼리 파라미터 필수 3개(String)
// client_id
// redirect_uri
// response_type(code)

// (Post)필수 파라미터로 토큰 받기
// if (요청 성공) {-> 토큰 받기 요청}
// else (error ) {error 응답}

// https://kauth.kakao.com/oauth/authorize?client_id=2573ecdca19c0b24e9bddc7d85b04084&redirect_uri=http://localhost:8080/kakao_loginkakao&response_type=code

// code = 2_lCNA07DXTAgeCtu5FAwKpWU7N7sw7oZ5zx8Bbu-lnx3Tl4asCF1i44mwIKPXMXAAABi3AUcPiUJG13ldIf8A


@Service
public class KakaoService {

    private final String KAKAO_AUTH_URI = "https://kauth.kakao.com/oauth/token";
    private final String API_KEY = "2573ecdca19c0b24e9bddc7d85b04084";
    private final String REDIRECT_URI = "http://localhost:8080/kakao_login";

    public HashMap<String, Object> getUserInfo(String accessToken){
        HashMap<String, Object> userInfo = new HashMap<>();

    }


    public String getKakaoLogin(){
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + API_KEY
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code";
    }

    //토큰 받기


    public String getKakaoToken(String code) throws Exception {
        if (code == null) throw new Exception("Failed get authorization code");

        String accessToken = "";
        String requestTokenUrl = "https://kauth.kakao.com/oauth/token";

        HttpHeaders httpHeaders = new Httphea


        try {



        }catch (){

        }


        return null;
    }
    //KakaoToken 생성


}
