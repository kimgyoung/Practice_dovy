package com.example.login_test.user;

import com.example.login_test.core.error.exception.Exception400;
import com.example.login_test.core.error.exception.Exception401;
import com.example.login_test.core.error.exception.Exception500;
import com.example.login_test.core.security.CustomUserDetails;
import com.example.login_test.core.security.JwtTokenProvider;
import com.example.login_test.kakao.KakaoApiResponse;
import com.example.login_test.kakao.KakaoService;
import com.example.login_test.kakao.KakaoTokenService;
import com.example.login_test.kakao.KakaoUserInforDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final KakaoService kakaoService;
    private final KakaoTokenService kakaoTokenService;

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

    public String login(UserRequest.JoinDTO requestDTO) {
        // ** 인증 작업.
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

            Authentication authentication =  authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );

            // ** 인증 완료 값을 받아온다.
            CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

            return JwtTokenProvider.create(customUserDetails.getUser());

        }catch (Exception e){
            throw new Exception401("인증 되지 않음.");
        }
    }
    public void checkEmail(String email){
        // 동일한 이메일이 있는지 확인.
        Optional<User> users = userRepository.findByEmail(email);
        if(users.isPresent()) {
            throw new Exception400("이미 존재 하는 이메일 입니다. : " + email);
        }
    }

    @Transactional
    public void kakaoJoin(UserRequest.KakaoJoinDTO kakaoJoinDTO, String code) throws JsonProcessingException {
        KakaoApiResponse kakaoApiResponse = kakaoService.getKakaoToken(code);
        String accessToken = kakaoTokenService.isAccessTokenValid(kakaoApiResponse.getAccess_token(), kakaoApiResponse.getRefresh_token());
        KakaoUserInforDto userInformation = kakaoService.getKakaoInfo(accessToken);
        kakaoJoinDTO.setEmail(userInformation.getEmail());
        kakaoJoinDTO.setUsername(userInformation.getNickname());
        String email = kakaoJoinDTO.getEmail();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            throw new Exception400("이미 존재하는 이메일입니다. : " + email);
        }
        User userToSave = kakaoJoinDTO.toEntity();
        try {
            userRepository.save(userToSave);
        } catch (Exception e) {
            throw new Exception500("회원 가입 중 에러가 발생했습니다: " + e.getMessage());
        }
    }

    public String kakaoLogin(UserRequest.KakaoLoginDTO kakaoLoginDTO, String code) throws JsonProcessingException {
        KakaoApiResponse kakaoApiResponse = kakaoService.getKakaoToken(code);
        String accessToken = kakaoTokenService.isAccessTokenValid(kakaoApiResponse.getAccess_token(), kakaoApiResponse.getRefresh_token());
        KakaoUserInforDto userInformation = kakaoService.getKakaoInfo(accessToken);
        kakaoLoginDTO.setEmail(userInformation.getEmail());
        Optional<User> user = userRepository.findByEmail(kakaoLoginDTO.getEmail());

        if (!user.isPresent()) {
            throw new Exception400("해당 이메일에 대한 사용자가 존재하지 않습니다.");
        }
        try {
            return JwtTokenProvider.create(user.get());
        } catch (Exception e) {
            throw new Exception500("JWT 토큰 생성 중 오류가 발생했습니다." + e.getMessage());
        }
    }
/*
    @Transactional
    public void printKakaoUserInfo(String email) {
        User user = userRepository.findByEmailAndIsKakaoUserTrue(email)
                .orElseThrow(() -> new Exception400("카카오 로그인 사용자가 존재하지 않습니다."));

        System.out.println("ID: " + user.getId());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Username: " + user.getUsername());
        System.out.println("PhoneNumber: " + user.getPhoneNumber());
        System.out.println("Roles: " + user.getRoles());
    }
 */
}