package com.example.product.user;

import com.example.product.error.exception.Exception400;
import com.example.product.error.exception.Exception401;
import com.example.product.security.CustomUserDetails;
import com.example.product.security.JwtTokenProvider;
import com.example.product.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {
    // 포폴용 쓸땐 서비스 클래스 만들어서 수정 하기
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Valid UserRequest.JoinDTO joinDTO, Error error){

        // 동일한 이메일 존재 하는 지 확인
        Optional<User> byEmail = userRepository.findByEmail(joinDTO.getEmail());

        // 존재 한다면 에러 발생
        if(byEmail.isPresent()){
            throw new Exception400("이미 존재 하는 이메일 입니다." + joinDTO.getEmail());
        }

        // 위의 두가지 다 확인 했다면
        // password 인코딩
        String encodedPassword = passwordEncoder.encode(joinDTO.getPassword());
        joinDTO.setPassword(encodedPassword);

        userRepository.save(joinDTO.toEntity());

        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserRequest.JoinDTO requestDto, Error error){
        String jwt = "";

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

            Authentication authentication =  authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );
            // ** 인증 완료 값을 받아 온다.
            CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
            // ** 토큰 발급.
            jwt = JwtTokenProvider.create(customUserDetails.getUser());

        }catch (Exception e){
            throw new Exception401("인증 되지 않음.");
        }
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt).body(ApiUtils.success(null));
    }


}
