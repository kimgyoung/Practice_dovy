package com.example.login_test.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class KakaoController {

    private final KakaoService kakaoService;

    @Autowired
    public KakaoController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/kakao_login")
    public ResponseEntity<?> login(@RequestParam(value = "code", required = false) String code) throws JsonProcessingException {
        if (code == null) {
            return ResponseEntity.ok(kakaoService.getKakaoLogin());
        }
        try {
            String accessToken = kakaoService.getKakaoToken(code);

            KakaoUserInforDto userInformation = kakaoService.getKakaoInfo(accessToken);

            Map<String, Object> responseData = new LinkedHashMap<>();
            responseData.put("accessToken", accessToken);
            responseData.put("userInformation", userInformation);

            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}


// 회원가입이 되어야 로그인이 되지.
// 카카오 로그인 후 구현 창,,? -> 회원가입완료 안내창? || 아무것도 없는 걍 홈페이지,,? 무늬만 이름만 쇼핑몰 이름해서

/*
Map<String, Object> responseData = new LinkedHashMap<>(); : LinkedHashMap 인스턴스를 생성하여 responseData에 저장
LinkedHashMap은 Map 인터페이스를 구현하는 클래스로, 키-값 쌍을 저장하며 삽입 순서를 유지

put: responseData 맵에 "accessToken"과 "userInformation"이라는 키로 각각 accessToken과 userInformation 객체를 저장

return ResponseEntity.ok(responseData); : ResponseEntity.ok() 메서드는 HTTP 상태 코드 200(OK)와 함께 제공된 객체를 본문으로 가지는 ResponseEntity 객체를 생성.
이 경우, responseData 맵이 본문으로 사용되므로, 이 맵에 저장된 데이터가 응답의 본문이 됨
따라서, accessToken과 userInformation 정보를 포함하는 HTTP 응답을 생성하고 반환.

 */
