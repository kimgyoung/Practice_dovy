package com.example.demo.user;

import com.example.demo.Util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
    private final UserRepository userRepository;

    // 도메인과 함수를 맵핑 시켜버림 -> 도메인 -> 자동으로 함수 실행

    /*  @Valid = 받아온 폼(데이터)의 유효성을 검사하는 역할을 수행.
                - @RequestBody @ModelAttribute 와 함께 사용한다.
                - DTO 에서 작성된 @Size @Pattern @  NotEmpty 등등을 검사.
                - 필드에 'NOT NULL' 조건이 있거나, 'UNIQUE' 조건이 설정되어 있는 경우도 확인.
    /*  @RequestBody = JSON으로 넘어오는 데이터를 UserRequest.LoginDTO 형태로 변경 해주는 역할
    */

    // public String 대신 ResponseEntity<?>, <?> = 와일드 카드

    // ** ResponseEntity<?> : JSON 변경
    // 변경할 데이터의 형태가 모두 다를 수 있기 때문에 <?> 형태로 반환 시켜 줌

    @PostMapping("/join")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO loginDTO) {

        //String byEmail = userService.findByEmail(loginDTO.getEmail());
        //return byEmail;

        // if (userService.function(loginDTO.getEmail()) == false) {
        // return "존재하지 않는 사용자 이메일입니다.";} 이런 형태로 사용 된다.

        // 이미 검증이 완료된 데이터
        //userService.findByEmail(loginDTO.getEmail());

        // 제이슨 파일로 바꿔 줘야 함
        //ApiUtils.success();

        userRepository.save(loginDTO.toEntity());

        return ResponseEntity.ok(ApiUtils.success(null));

        // 성공에 대한 것
        // 이 null 값이 ApiUtils의 T responsefh 로 가서 -> public ApiResult (T response)의 response를 필드의 (this response->)로 넣고
        // true 고 success 이지만 응 데이터는 null 이야. 없어


        //return "{\"email\":\"admin@green.com\"}";

        //return ResponseEntity.ok(ApiUtils.error(null));
        //return ResponseEntity.ok(ApiUtils.error("ssaabda"));
    }

}
