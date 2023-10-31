package com.example.login_test.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

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


    // 유저 추가 정보

    public String getKakaoInfo(String accessToken) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                        HttpMethod.GET,
                        entity,
                        String.class);
        /*
        ObjectMapper mapper = new ObjectMapper();
        KakaoData kakaoData = mapper.readValue(responseEntity.getBody(), KakaoData.class);

        System.out.println("access_token: "+ kakaoData.getAccess_token());
        System.out.println("token_type: "+ kakaoData.getToken_type());
        System.out.println("expires_in: "+ kakaoData.getExpires_in());
        System.out.println("scope: "+ kakaoData.getScope());
        System.out.println("refresh_token: "+ kakaoData.getRefresh_token());
        System.out.println("refresh_token_expires_in: "+ kakaoData.getRefresh_token_expires_in());
         */

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            System.out.println(responseEntity.getBody());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);

            Long id = jsonObject.get("id").getAsLong();

            JsonObject properties = jsonObject.getAsJsonObject("properties");
            String nickname = properties != null && properties.has("nickname") ? properties.get("nickname").getAsString() : null;


            //String nickname = jsonObject.getAsJsonObject("properties").get("nickname").getAsString();
            //String email = jsonObject.getAsJsonObject("kakao_account").get("email").getAsString();

            // 필요에 따라 id, nickname, email을 사용하거나 반환하세요.
            // 예를 들어, 사용자 정보를 문자열로 반환하는 경우:
            return "id: " + id + ", nickname: " + nickname;

            // JSON 문자열에는 사용자 정보가 포함되어 있습니다.
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get user info");
        }
    }
}
/*
요청
헤더
Authorization	Authorization: KakaoAK ${SERVICE_APP_ADMIN_KEY}
                인증 방식, 서비스 앱 어드민 키로 인증 요청
Content-type	Content-type: application/x-www-form-urlencoded;charset=utf-8
                요청 데이터 타입

쿼리 파라미터
target_id_type	String	회원번호 종류, user_id
target_id	    Long	사용자 정보를 가져올 사용자의 회원번호

응답
id	            Long	회원번호

 */
