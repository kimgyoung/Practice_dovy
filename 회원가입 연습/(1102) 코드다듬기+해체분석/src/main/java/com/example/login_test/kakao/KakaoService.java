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

    // 컨롤에서 이 메서드를 호출
    // 카카오 로그인 페이지로 리다이렉트 하기 위한 URL 생성(return 카카오 로그인 페이지로)
    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/authorize"
                + "?client_id=" + API_KEY
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code";
    }

    // 코드를 가져와서 카카오 토큰을 가져오는 메서드
    public String getKakaoToken(String code) {
        // 인증 코드가 없으면 예외를 발생 시킴
        if (code == null) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Failed get authorization code");

        // HTTP 헤더 객체 생성 후 헤더에 컨텐트 타입과 벨류 값 저장
        // 키 값 - 타입 , 밸류 - 요청 본문이 url 인코딩된 형태로 전송되어야 하고 UTF-8문자 사용
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // MultiValueMap 하나의 키에 여러개의 값을 연결할 수 있음.
        // LinkedMultiValueMap 인스턴스를 생성하여 params에 저장
        // 각각의 "키", "값" 쌍을 추가
        // 맵에 저장된 파라미터가 HTTP요청에 포함 됨
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type"   , "authorization_code");
        params.add("client_id"    , API_KEY);
        params.add("redirect_uri" , REDIRECT_URI);
        params.add("code"         , code);

        //params에 설정된 파라미터와 headers에 설정된 헤더를 포함하는 HTTP 요청을 생성하고,
        // 이를 RestTemplate을 통해 보내는 작업을 준비

        RestTemplate restTemplate = new RestTemplate();

        // HttpEntity 생성자에서 (R body,MultiValueMap<String, String> headers)
        // 이므로 순서는 무조건 (본문,헤더) 여야함 즉 (params, headers) 순서 바뀌면 안됨
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        // RestTemplate의 exchange 메서드를 사용하여 HTTP 요청을 보내고, 그 응답을 ResponseEntity 객체로 받는 부분
        //KAKAO_AUTH_URI + "/token" URL로 POST 요청을 보내고, 그 응답을 String 타입의 ResponseEntity 객체로 받는 것
        //이 요청에는 httpEntity에 설정된 본문과 헤더가 포함
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(KAKAO_AUTH_URI + "/token", // 수정된 부분
                        HttpMethod.POST,
                        httpEntity,
                        String.class);

        //서버로부터 받은 HTTP 응답을 확인
        // HTTP 응답의 본문을 JSON 객체로 변환
        // responseEntity.getBody()는 HTTP 응답의 본문을 반환
        // gson.fromJson(...)은 이 본문을 JsonObject로 변환
        // 변환된 JSON 객체에서 "access_token" 키의 값을 문자열로 가져와서 반환. (카카오 서버에서 발급한)
        // 응답이 성공적인 경우 응답 본문에서 액세스 토큰을 추출하여 반환
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

        // "Authorization" 헤더: 클라이언트가 서버에게 자신의 신원을 증명 하는데 사용
        // Bearer " 뒤에 액세스 토큰을 붙여서 이 헤더 값을 설정: 클라이언트가 이 액세스 토큰을 가지고 있음을 서버에 알리는 것
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        // 이 요청에는 본문이 필요없어서 헤더만 사용(가능)
        // 보통은 (본문,헤더) 가 일반적임
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                        HttpMethod.POST,
                        entity,
                        String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            Gson gson = new Gson();

            // kakaoUserInforResponse 객체로 변환
            KakaoUserInforResponse kakaoUserInforResponse = gson.fromJson(responseEntity.getBody(), KakaoUserInforResponse.class);

            // kakaoUserInforResponse 객체에서 값을 가져오기
            String nickname = kakaoUserInforResponse.getProperties().getNickname();
            String profile_image = kakaoUserInforResponse.getProperties().getProfile_image();
            String email = kakaoUserInforResponse.getKakao_account().getEmail();

            System.out.println(nickname);
            System.out.println(email);
            System.out.println(profile_image);

            //가져온 정보를 KakaoUserInforDto 객체에 담아 반환
            return new KakaoUserInforDto(nickname, email, profile_image);

        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get user info");
        }
    }
}

/*
헤더를 설정하는 이유
통신 과정에서 필요한 추가 정보를 전달하고, 메시지를 올바르게 처리하기 위해서입니다.
헤더 없이 메시지만 보내면, 수신자는 메시지를 어떻게 처리해야 할지, 메시지가 어떤 내용을 담고 있는지, 메시지를 보낸 클라이언트가 누구인지 등을 알 수없음
헤더를 통해 이런 정보를 명시적으로 전달함으로써, 웹 서버나 클라이언트는 메시지를 효과적으로 처리.
 */

/*
"grant_type"은 인증 코드 흐름을 나타내는 'authorization_code'로 설정되어 있
"grant_type"은 OAuth 2.0 인증 프로토콜에서 사용되는 파라미터로, 사용할 인증 방식을 나타냄
"authorization_code"는 그 중 하나의 인증 방식을 가리키며, 이는 일반적으로 서버 사이드 애플리케이션에서 많이 사용됩
authorization_code" 방식은 클라이언트가 사용자에게서 직접적으로 사용자의 인증 정보를 받지 않고,
대신 인증 서버로부터 인증 코드를 받아 이를 사용해 액세스 토큰을 얻는 방식 이 방식을 사용하면 보안성이 향상
클라이언트는 인증 코드 흐름을 사용하여 액세스 토큰을 요청하겠다는 의미를 전달하는 것


"client_id"는 클라이언트 애플리케이션의 ID, 이는 일반적으로 서비스 제공자로부터 발급받
"redirect_uri"는 인증 코드가 전달될 URI
"code"는 사용자로부터 받은 인증 코드
 */

/*
RestTemplate은 Spring Framework에서 제공하는 HTTP 클라이언트를 위한 클래스.
이 클래스를 사용하면 HTTP 서버에 요청을 보내고 응답을 받는 데 필요한 많은 작업들을 쉽게 처리
RestTemplate은 다양한 HTTP 메서드(GET, POST, PUT, DELETE 등)를 위한 메서드들을 제공합니다. 이 메서드들을 사용하면,
HTTP 요청을 생성하고, 요청을 보내고, 응답을 받아 처리하는 작업을 한 줄의 코드로 수행
또한, HTTP 메시지 컨버터를 사용하여 HTTP 요청의 본문을 생성하거나, HTTP 응답의 본문을 Java 객체로 변환하는 작업을 자동으로 처리
예를 들어, JSON 형식의 HTTP 응답을 자동으로 Java 객체로 변환하거나, Java 객체를 JSON 형식의 HTTP 요청 본문으로 변환하는 등의 작업을 수행
 */

/*
HttpEntity는 HTTP 메시지를 구성하는 요소 중 하나. H크게 헤더(header)와 본문(body)으로 구성.
헤더는 메시지에 대한 메타데이터(예: 내용의 형식, 인코딩 방식 등)를 나타내고, 본문은 실제 전달하려는 데이터(예: HTML 문서, JSON 객체 등)를 담음
 */

/*
restTemplate.exchange(...): RestTemplate의 exchange 메서드는 HTTP 요청을 보내고 ResponseEntity 객체를 반환
ResponseEntity는 HTTP 응답을 나타내는 객체로, 응답의 헤더, 본문, 상태 코드 등을 포함
KAKAO_AUTH_URI + "/token": HTTP 요청을 보낼 URL입니다. KAKAO_AUTH_URI는 카카오 인증 서버의 주소를 담은 변수이고,
"/token"은 토큰을 요청하는 API의 경로입니다. 이 둘을 연결하여 완전한 URL을 만듭니다.
HttpMethod.POST: 사용할 HTTP 메서드. 이 경우에는 POST 메서드를 사용하여 요청을 보냅니다.
httpEntity: HTTP 요청의 본문과 헤더를 담은 HttpEntity 객체. 이전에 설명드린 new HttpEntity<>(params, headers); 코드로 생성된 객체
String.class: 응답의 본문을 어떤 타입으로 변환할지를 나타냄. 이 경우에는 응답의 본문을 String 타입으로 변환.
 */