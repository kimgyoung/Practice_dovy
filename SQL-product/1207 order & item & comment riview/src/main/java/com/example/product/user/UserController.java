package com.example.product.user;

import com.example.product.security.JwtTokenProvider;
import com.example.product.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Valid UserRequest.JoinDTO joinDTO, Error error){
        userService.join(joinDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserRequest.JoinDTO requestDto, Error error){
        String jwt = userService.login(requestDto);
        System.out.println(jwt);
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt).body(ApiUtils.success(null));
    }

}
