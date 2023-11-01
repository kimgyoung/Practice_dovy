package com.example.login_test.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class KakaoController {

    private final KakaoService kakaoService;

    @Autowired
    public KakaoController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/kakao_login")
    public ResponseEntity<?> kakao_login(@RequestParam(value = "code", required = false) String code) throws JsonProcessingException {
        if (code == null) {
            // 인증 코드가 전달되지 않았다면 로그인 URL을 반환
            String loginUrl = kakaoService.getKakaoLogin();
            return ResponseEntity.ok().body(loginUrl);
        } else {
            // 인증 코드가 전달되었다면 액세스 토큰을 얻음
            String accessToken;
            try {
                accessToken = kakaoService.getKakaoToken(code);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
            System.out.println("access_token : " + accessToken);


            String userInformation = kakaoService.getKakaoInfo(accessToken);
            /*
            URI redirectUri = URI.create("http://localhost:8080/");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(redirectUri);

             */
            //return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);


            return ResponseEntity.ok().body(new MsgEntity("로그인 성공", accessToken, userInformation));
        }
    }

}
