package com.example.login_test.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.logging.Logger;

@Service
public class KakaoService {

    private static final Logger logger = Logger.getLogger(KakaoService.class.getName());

    @Value("${kakao.api.key}")
    private String API_KEY;
    @Value("${kakao.api.authUri}")
    private String KAKAO_AUTH_URI;
    @Value("${kakao.api.redirectUri}")
    private String REDIRECT_URI;
    @Value("${kakao.api.logoutRedirectUri}")
    private String LOGOUT_REDIRECT_URI;

    @Autowired
    private TokenRepository tokenRepository;

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
                restTemplate.exchange(KAKAO_AUTH_URI + "/token",
                        HttpMethod.POST,
                        httpEntity,
                        String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            KakaoApiResponse kakaoApiResponse = gson.fromJson(responseEntity.getBody(),KakaoApiResponse.class);

            // 토큰 정보를 Token 객체에 저장
            Token token = new Token();
            token.setAccessToken(kakaoApiResponse.getAccess_token());
            token.setRefreshToken(kakaoApiResponse.getRefresh_token());
            token.setExpiresIn(System.currentTimeMillis() / 1000 + kakaoApiResponse.getExpires_in());
            token.setRefreshTokenExpiresIn(System.currentTimeMillis() / 1000 + kakaoApiResponse.getRefresh_token_expires_in());

            // 토큰 정보를 데이터베이스에 저장
            tokenRepository.save(token);


            // 출력 확인용 ++
            String access_token = kakaoApiResponse.getAccess_token();
            String refresh_token = kakaoApiResponse.getRefresh_token();
            String token_type = kakaoApiResponse.getToken_type();
            int expires_in = kakaoApiResponse.getExpires_in();
            int refresh_token_expires_in = kakaoApiResponse.getRefresh_token_expires_in();
            String scope = kakaoApiResponse.getScope();

            logger.info("Access Token: " + access_token);
            logger.info("Refresh Token: " + refresh_token);
            logger.info("Token Type: " + token_type);
            logger.info("Expires In (seconds): " + expires_in);
            logger.info("Refresh Token Expires In (seconds): " + refresh_token_expires_in);
            logger.info("Scope: " + scope);

            return access_token;
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get access token");
        }
    }

    public KakaoAccount getKakaoInfo(String accessToken) throws JsonProcessingException {
        // 토큰 만료 확인
        Token token = tokenRepository.findByAccessToken(accessToken);
        if (token.getExpiresIn() <= System.currentTimeMillis() / 1000) {
            // 토큰이 만료되었으므로 새로운 토큰을 받아옴
            accessToken = refreshKakaoToken(token.getRefreshToken());
        }

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
            JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);
            JsonObject kakaoAccountObject = jsonObject.getAsJsonObject("kakao_account");
            String email = kakaoAccountObject.get("email").getAsString();

            JsonObject profileObject = kakaoAccountObject.getAsJsonObject("profile");
            String username = profileObject.get("nickname").getAsString();

            return new KakaoAccount(email, username);
        }
        else {
            System.out.println("Failed to get access token. Response: " + responseEntity.getBody());
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get user info");
        }
    }

    public String refreshKakaoToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", API_KEY);
        params.add("refresh_token", refreshToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(KAKAO_AUTH_URI + "/token",
                        HttpMethod.POST,
                        httpEntity,
                        String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            KakaoApiResponse kakaoApiResponse = gson.fromJson(responseEntity.getBody(),KakaoApiResponse.class);

            // 새로 발급받은 액세스 토큰 반환
            return kakaoApiResponse.getAccess_token();
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to refresh access token");
        }
    }

    public String KakaoLogout() {
        return "https://kauth.kakao.com/oauth/logout?client_id=" + API_KEY + "&logout_redirect_uri=" + LOGOUT_REDIRECT_URI;
    }

    // 로그아웃 예시
    public void logout(String userId) {
        // 사용자의 모든 토큰 삭제
        tokenRepository.deleteByUserId(userId);

        // 기타 로그아웃 처리...
    }

}
