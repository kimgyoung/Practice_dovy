package com.example.login_test.kakao;

import com.example.login_test.user.User;
import com.example.login_test.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @Autowired
    public KakaoController(KakaoService kakaoService, UserService userService) {
        this.kakaoService = kakaoService;
        this.userService = userService;
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
            //responseData.put("accessToken", accessToken);
            responseData.put("userInformation", userInformation);


            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("http://localhost:8080/kakao_register"));

            //return ResponseEntity.ok(responseData);
            return new ResponseEntity<>(headers, HttpStatus.FOUND);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/kakao_register")
    public ResponseEntity<?> register(@RequestBody KakaoUserInforDto kakaoUserInforDto) {
        User user = userService.registerKakaoUser(kakaoUserInforDto);
        return ResponseEntity.ok(user);
    }
}

