package com.example.login_test.kakao;

import com.example.login_test.core.utils.ApiUtils;
import com.example.login_test.user.UserRequest;
import com.example.login_test.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;

@RequiredArgsConstructor
@Controller
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @GetMapping("/kakao_login")
    public String login(@RequestParam(value = "code", required = false) String code, HttpSession session) {
        if (code == null) {
            return "redirect:" + kakaoService.getKakaoLogin();
        }
        try {
            String accessToken = kakaoService.getKakaoToken(code);
            KakaoAccount kakaoAccount = kakaoService.getKakaoInfo(accessToken);
            session.setAttribute("accessToken", accessToken);
            session.setAttribute("userInformation", kakaoAccount);
            return "redirect:/choseJoin.html";
        } catch (Exception e) {
            return "error_page";
        }
    }

    @PostMapping("/kakaoJoin")
    public ResponseEntity<?> kakaoJoin(HttpSession session,UserRequest.KakaoJoinDTO kakaoUser) {

        KakaoAccount userInformation = (KakaoAccount) session.getAttribute("userInformation");
        if (userInformation == null) {
            throw new IllegalArgumentException("세션에 사용자 정보가 존재하지 않습니다.");
        }
        kakaoUser.setEmail(userInformation.getEmail());
        kakaoUser.setUsername(userInformation.getUsername());
        userService.kakaoJoin(kakaoUser);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/kakao_logout")
    public ResponseEntity<?> logout() {
        String logoutUrl = kakaoService.KakaoLogout();
        return ResponseEntity.ok().body(logoutUrl);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView logout(ModelAndView mav) {
        mav.setViewName("redirect:/");
        return mav;
    }

}
