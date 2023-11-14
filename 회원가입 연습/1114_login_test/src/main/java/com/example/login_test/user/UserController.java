package com.example.login_test.user;

import com.example.login_test.core.security.JwtTokenProvider;
import com.example.login_test.core.utils.ApiUtils;
import com.example.login_test.kakao.KakaoService;
import com.example.login_test.kakao.KakaoTokenService;
import com.example.login_test.kakao.KakaoUserInforDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {

        userService.join(requestDTO);

        return ResponseEntity.ok( ApiUtils.success(null) );
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {

        userService.checkEmail(requestDTO.getEmail());

        return ResponseEntity.ok( ApiUtils.success(null) );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {

        String jwt = userService.login(requestDTO);

        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt)
                .body(ApiUtils.success(null));
    }

    @PostMapping("/kakaoJoin")
    public ResponseEntity<?> kakaoJoin(@RequestBody @Valid UserRequest.KakaoJoinDTO kakaoJoinDTO, String refreshToken) throws JsonProcessingException {

        userService.kakaoJoin(kakaoJoinDTO,refreshToken);

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/kakaoLogin")
    public ResponseEntity<?> kakaoLogin(@RequestBody @Valid UserRequest.KakaoLoginDTO kakaoLoginDTO, String refreshToken) throws JsonProcessingException {

        String jwt = userService.kakaoLogin(kakaoLoginDTO, refreshToken);

        // JWT 토큰을 헤더에 담아 응답
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt)
                .body(ApiUtils.success(null));
    }
}

