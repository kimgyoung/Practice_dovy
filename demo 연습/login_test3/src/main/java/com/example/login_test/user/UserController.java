package com.example.login_test.user;

import com.example.login_test.core.security.JwtTokenProvider;
import com.example.login_test.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    /* @Valid = 받아온 폼의 데이터 유효성을 검사 하는 역할을 수행.
     *  - @RequestBody, @ModelAttribute 와 함께 사용한다.
     *  - DTO에서 작성된  @Size, @Pattern, @NotEmpty 등등을 검사.
     *  - 필드에 'NOT NULL' 조건이 있거나, 'UNIQUE' 조건이 설정 되어 있는 경우도 확인.
     *
     * @RequestBody
     * JSON 으로 넘어 오는 데이터를 UserRequest.LoginDTO 형태로 변경 해주는 역할.
     * */

    @PostMapping("/join")
    public ResponseEntity<?> join (@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {
        userService.join(requestDTO);
        return ResponseEntity.ok(ApiUtils.success(null) );
    }

    @PostMapping("/check")
    public ResponseEntity<?> check (@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error){
        userService.checkEmail(requestDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(null));
    }


    // ** 인증 작업
    // Securtiy
    @PostMapping("/Login")
    public ResponseEntity<?> Login (@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {

        String jwt = userService.login(requestDTO);

        // 그 토큰을 받아 오면 헤더로 넣어라, jwt
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt)
                .body(ApiUtils.success(null));
    }


}
























