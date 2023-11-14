package com.example.login_test.kakao;

import com.example.login_test.core.security.JwtTokenProvider;
import com.example.login_test.core.utils.ApiUtils;
import com.example.login_test.user.User;
import com.example.login_test.user.UserRequest;
import com.example.login_test.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao_login")
    public String login(@RequestParam(value = "code", required = false) String code, Model model) {
        if (code == null) {
            return "redirect:" + kakaoService.getKakaoLogin();
        }
        try {
            String accessToken = String.valueOf(kakaoService.getKakaoToken(code));
            KakaoUserInforDto userInformation = kakaoService.getKakaoInfo(accessToken);
            model.addAttribute("userInformation", userInformation);
            model.addAttribute("code", code);
            return "choseJoin";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error_page";
        }
    }
}