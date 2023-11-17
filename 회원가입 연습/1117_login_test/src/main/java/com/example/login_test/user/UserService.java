package com.example.login_test.user;

import com.example.login_test.core.error.exception.Exception400;
import com.example.login_test.core.error.exception.Exception401;
import com.example.login_test.core.error.exception.Exception500;
import com.example.login_test.core.security.CustomUserDetails;
import com.example.login_test.core.security.JwtTokenProvider;

import com.example.login_test.kakao.Token;
import com.example.login_test.kakao.TokenRepository;
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

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    //private final Logger logger = LoggerFactory.getLogger(UserService.class);
/*
    public String kakaoLogin(UserRequest.KakaoLoginDTO kakaoUser) {
        Optional<User> user = userRepository.findByEmail(kakaoUser.getEmail());
        if (!user.isPresent()) {
            throw new Exception401("해당 이메일에 대한 사용자가 존재하지 않습니다. : " + kakaoUser.getEmail());
        }
        String jwt = JwtTokenProvider.create(user.get());
        logger.info("JWT token has been created: {}", jwt);
        return jwt;
    }

 */

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        checkEmail(requestDTO.getEmail());

        String encodedPassword = passwordEncoder.encode( requestDTO.getPassword());

        requestDTO.setPassword(encodedPassword);

        try {
            userRepository.save(requestDTO.toEntity());

        }catch (Exception e){
            throw new Exception500(e.getMessage());
        }
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        // ** 인증 작업.
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

            Authentication authentication =  authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );

            // ** 인증 완료 값을 받아온다.
            CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

            // ** 토큰 발급.
            return JwtTokenProvider.create(customUserDetails.getUser());

        }catch (Exception e){
            throw new Exception401("인증 되지 않음.");
        }
    }

    public void checkEmail(String email){
        Optional<User> users = userRepository.findByEmail(email);
        if(users.isPresent()) {
            throw new Exception400("이미 존재 하는 이메일 입니다. : " + email);
        }
    }

    @Transactional
    public User kakaoJoin(UserRequest.KakaoJoinDTO kakaoUser) {
        Optional<User> user = userRepository.findByEmail(kakaoUser.getEmail());

        if (user.isPresent()) {
            throw new Exception400("이미 존재하는 이메일입니다. : " + kakaoUser.getEmail());
        }
        try {
            userRepository.save(kakaoUser.toEntity());
        } catch (Exception e) {
            throw new Exception500(e.getMessage());
        }
    }

    public String kakaoLogin(UserRequest.KakaoLoginDTO kakaoUser) {
        Optional<User> userOptional = userRepository.findByEmail(kakaoUser.getEmail());
        if (!userOptional.isPresent()) {
            throw new Exception401("해당 이메일에 대한 사용자가 존재하지 않습니다. : " + kakaoUser.getEmail());
        }

        User user = userOptional.get();
        String jwt = JwtTokenProvider.create(user);

        // 토큰 정보를 Token 객체에 설정
        Token token = new Token();
        token.setAccessToken(jwt);
        token.setUserId(String.valueOf(user.getId()));  // User 객체에서 사용자 ID를 가져와 Token 객체에 설정

        // TokenRepository를 이용하여 토큰 정보를 데이터베이스에 저장
        tokenRepository.save(token);

        return jwt;
    }


}
