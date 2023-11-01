package com.example.login_test.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {

    private final String KAKAO_AUTH_URI = "https://kauth.kakao.com/oauth";
    private final String API_KEY = "2573ecdca19c0b24e9bddc7d85b04084";
    private final String REDIRECT_URI = "http://localhost:8080/kakao_login";

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/authorize"
                + "?client_id=" + API_KEY
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code";
    }

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

    public String getKakaoInfo(String accessToken) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                        HttpMethod.POST,
                        entity,
                        String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            System.out.println(responseEntity.getBody());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);
            JsonObject properties = jsonObject.getAsJsonObject("properties");
            JsonObject kakao_account = jsonObject.getAsJsonObject("kakao_account");

            //Long id = jsonObject.get("id").getAsLong();

            String email = kakao_account.get("email").getAsString();

            String nickname = properties.get("nickname").getAsString();
            String profile_image = properties.get("profile_image").getAsString();

            // 필요에 따라 id, nickname, email을 사용하거나 반환하세요.



            return "1. Nickname: " + nickname + "2. Email: " + email + "3. Profile Image: " + profile_image;

            // JSON 문자열에는 사용자 정보가 포함되어 있습니다.
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get user info");
        }
    }
}