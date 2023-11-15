package com.example.login_test.kakao;

import com.example.login_test.core.utils.ApiUtils;
import com.example.login_test.user.UserRequest;
import com.example.login_test.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.ref.PhantomReference;

@RequiredArgsConstructor
@Controller
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @GetMapping("/kakao_login")
    public String login(@RequestParam(value = "code", required = false) String code, Model model, HttpSession session) throws JsonProcessingException {
        if (code == null) {
            return "redirect:" + kakaoService.getKakaoLogin();
        }
        try {
            String accessToken = kakaoService.getKakaoToken(code);
            // 카카오 API로부터 사용자 정보 받아오기

            KakaoAccount userInformation = kakaoService.getKakaoInfo(accessToken);

            /*
            // KakaoJoinDTO 객체 생성 및 필드 설정
            UserRequest.KakaoJoinDTO kakaoUser = new UserRequest.KakaoJoinDTO();
            kakaoUser.setEmail(userInformation.getEmail());
            kakaoUser.setUsername(userInformation.getUsername());  // 'username' 필드 설정

            // UserService의 kakaoJoin 메소드 호출
            userService.kakaoJoin(kakaoUser);

             */

            // 세션에 액세스 토큰과 사용자 정보 저장
            session.setAttribute("accessToken", accessToken);
            session.setAttribute("userInformation", userInformation);

            model.addAttribute("userInformation", userInformation);
            model.addAttribute("code", code);

            return "choseJoin";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error_page";
        }
    }
    @PostMapping("/kakaoJoin")
    public ResponseEntity<?> kakaoJoin(HttpSession session) {
        // 세션에서 사용자 정보 받아오기
        KakaoAccount userInformation = (KakaoAccount) session.getAttribute("userInformation");

        if (userInformation == null) {
            throw new IllegalArgumentException("세션에 사용자 정보가 존재하지 않습니다.");
        }

        // KakaoJoinDTO 객체 생성 및 필드 설정
        UserRequest.KakaoJoinDTO kakaoUser = new UserRequest.KakaoJoinDTO();
        kakaoUser.setEmail(userInformation.getEmail());
        kakaoUser.setUsername(userInformation.getUsername());  // 'username' 필드 설정

        // UserService의 kakaoJoin 메소드 호출
        userService.kakaoJoin(kakaoUser);

        return ResponseEntity.ok(ApiUtils.success(null));
    }


}