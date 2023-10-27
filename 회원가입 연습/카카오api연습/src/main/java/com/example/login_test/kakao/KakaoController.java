package com.example.login_test.kakao;

import com.example.login_test.core.utils.ApiUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class KakaoController {

    public KakaoService kakaoService;


    @PostMapping("/kakao_login")
    public ResponseEntity<?> kakaoLogin (@RequestParam("code") String code) throws Exception {
        String accessToken = kakaoService.getKakaoToken(code);

        System.out.println("code : " + code);

        //kakaoService.getKakaoLogin();

        return ResponseEntity.ok( ApiUtils.success(null) );
    }

}
