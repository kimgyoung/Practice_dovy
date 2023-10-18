package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


// ** 불러와서 생성자 초기화
/*
public UserController(UserService userService) {
        this.userService = userService;
}
 */
// ** 생성자를 통해 UserService 를 초기화 하는 대신 @RequiredArgsConstructor 어노테이션을 사용

@RequiredArgsConstructor
@RestController
public class UserController {

    // ** final 붙여서
    private final UserService userService;

    // 도메인과 함수를 맵핑 시켜버림 -> 도메인 -> 자동으로 함수 실행
    @GetMapping("/join")
    public String login(UserRequest.loginDTO loginDTO) {

//        if (userService.function(loginDTO.getEmail()) == false) {
//            return "존재하지 않는 사용자 이메일입니다.";
//        } 이런 형태로 사용된다.

        String byEmail = userService.findByEmail(loginDTO.getEmail());
        //String byEmail = "없음";

        return byEmail;
    }

}
