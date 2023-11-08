package com.example.login_test.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class KakaoService {

    @Value("${kakao.api.key}")
    private String API_KEY;

    @Value("${kakao.api.authUri}")
    private String KAKAO_AUTH_URI;

    @Value("${kakao.api.redirectUri}")
    private String REDIRECT_URI;

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/authorize"
                + "?client_id=" + API_KEY
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code";
    }

    // 코드를 가져와서 카카오 토큰을 가져오는 메서드
    public String getKakaoToken(String code) {
        if (code == null) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Failed get authorization code");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type"   , "authorization_code");
        params.add("client_id"    , API_KEY);
        params.add("redirect_uri" , REDIRECT_URI);
        params.add("code"         , code);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(KAKAO_AUTH_URI + "/token", // 수정된 부분
                        HttpMethod.POST,
                        httpEntity,
                        String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);

            return jsonObject.get("access_token").getAsString();
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get access token");
        }
    }

    //액세스 토큰으로 카카오 API를 통해 사용자 정보를 가져옴
    public KakaoUserInforDto getKakaoInfo(String accessToken) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                        HttpMethod.POST,
                        entity,
                        String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            Gson gson = new Gson();
            KakaoUserInforResponse kakaoUserInforResponse = gson.fromJson(responseEntity.getBody(), KakaoUserInforResponse.class);

            String nickname = kakaoUserInforResponse.getProperties().getNickname();
            String profile_image = kakaoUserInforResponse.getProperties().getProfile_image();
            String email = kakaoUserInforResponse.getKakao_account().getEmail();

            System.out.println(nickname);
            System.out.println(email);
            System.out.println(profile_image);

            //가져온 정보를 KakaoUserInforDto 객체에 담아 반환
            return new KakaoUserInforDto(nickname, email, profile_image);

        } else {
            System.out.println("Failed to get access token. Response: " + responseEntity.getBody());
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get user info");
        }
    }
}

