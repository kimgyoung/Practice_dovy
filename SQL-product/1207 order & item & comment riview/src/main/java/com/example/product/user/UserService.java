package com.example.product.user;

import com.example.product.error.exception.Exception400;
import com.example.product.error.exception.Exception401;
import com.example.product.security.CustomUserDetails;
import com.example.product.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void join(UserRequest.JoinDTO joinDTO) {
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
    }

    @Transactional
    public String login(UserRequest.JoinDTO requestDto) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

            // anonymousUser = 비인증
            Authentication authentication = authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );
            // ** 인증 완료 값을 받아 온다.
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            // ** 토큰 발급.
            return JwtTokenProvider.create(customUserDetails.getUser());

        } catch (Exception e) {
            throw new Exception401("인증 되지 않음.");
        }
    }
}
