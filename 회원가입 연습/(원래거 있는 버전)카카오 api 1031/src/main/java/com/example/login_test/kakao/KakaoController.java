package com.example.login_test.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoController {

    private final KakaoService kakaoService;

    @Autowired
    public KakaoController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/kakao_login")
    public Object oauth(@RequestParam(value = "code", required = false) String code) {
        if (code == null) {
            // 인증 코드가 전달되지 않았다면 로그인 URL을 반환
            return kakaoService.getKakaoLogin();
        } else {
            // 인증 코드가 전달되었다면 액세스 토큰을 얻음
            String accessToken;
            try {
                accessToken = kakaoService.getKakaoToken(code);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
            System.out.println("access_token : " + accessToken);

            // 추가적으로 필요한 작업이 있다면 여기에 구현하세요.
            // 예: 카카오 사용자 정보 가져오기, 로그인 세션 생성 등

            return ResponseEntity.ok().body(new MsgEntity("Success", accessToken));

        }
    }

    @GetMapping("/userinfo")
    public ResponseEntity<String> getUserInfo(@RequestParam("token") String accessToken) {
        try {
            String userInfo = kakaoService.getKakaoInfo(accessToken);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
