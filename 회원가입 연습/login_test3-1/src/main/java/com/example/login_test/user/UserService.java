package com.example.login_test.user;

import com.example.login_test.core.error.exception.Exception400;
import com.example.login_test.core.error.exception.Exception401;
import com.example.login_test.core.error.exception.Exception500;
import com.example.login_test.core.security.CustomUserDetails;
import com.example.login_test.core.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/* 메서드나 클래스에 적용가능.
 * Transactional
 * 어노테이션이 적용된 메서드가 호출되면, 새로운 트랜잭션이 시작됨.
 * 메서드 실행이 성공적으로 완료되면, 트랜잭션은 자동으로 커밋.
 * 메서드 실행 중에 예외가 발생하면, 트랜잭션은 자동으로 롤백.
 *
 ** readOnly = true : 이 설정은 해당 트랜잭션이 데이터를 변경하지 않고 읽기 전용으로만 사용이 가능하다는 것을 명시적으로 나타냄.
 * */
// 무조건 읽기 전용으로만 쓰겠다.
// 읽기 전용으로 하면 뭘 못 받으니까 @트랜젝셔널 join 함수에 추가 해 줌

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service

public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 동일한 이메일이 있는지 확인
    // 추가
    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {

        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        System.out.println("Final Hash: " + encodedPassword);

        requestDTO.setPassword(encodedPassword);

        requestDTO.toEntity().output();
        checkEmail(requestDTO.getEmail());

        try {
            userRepository.save(requestDTO.toEntity());
        }catch (Exception e){
            throw new Exception500(e.getMessage());
        }
    }

    public String login(UserRequest.JoinDTO requestDTO){
        // ** 인증 작업
        try {
            // - 최초의 유저 값
            // 커스텀 유저 디테일 & 디테일 서비스랑 연관
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );

            // ** 인증 완료 값을 받아 온다.
            CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

            // ** 토큰 발급
            return JwtTokenProvider.create(customUserDetails.getUser());
            // 들어온 값의 유저로 토큰을 발급 해서 넘겨라!
            //return "Bearer "+ "인증 값";

        }catch (Exception e){
            // 인증 되지 않았 다면 401 반환
            throw new Exception401("인증 되지 않음.");
        }
    }

    // 탐색
    public void checkEmail(String email){
        Optional<User> users = userRepository.findByEmail(email);
        if(users.isPresent()){
            throw new Exception400("이미 존재 하는 이메일 입니다.: " + email);
        }
    }

    public void findAll(){
        List <User> all = userRepository.findAll();
        for(User user: all){
            user.output();
        }
    }
}
