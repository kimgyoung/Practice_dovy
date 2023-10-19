package com.example.login_test.user;

import lombok.RequiredArgsConstructor;

//import org.springframework.security.crypto.password.PasswordEncoder;

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
 ** readOnly = true : 이 설정은 해당 트랜잭션이 데이터를 변경하지 않고 읽기전용으로만 사용이 가능하다는것을 명시적으로 나타냄.
 * */
// 무조건 읽기 전용으로만 쓰겠다.
// 읽기 전용으로 하면 뭘 못받으니까 @트랜젝셔널 join함수에 추가해줌

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(UserRequest.LoginDTO requestDTO) {

        // 동일한 이메일이 있는지 확인
        Optional<User> users = userRepository.findByEmail(requestDTO.getEmail());

        if(users.isPresent()){
            throw new RuntimeException("이미 존재하는 이메일 입니다.");
        }



        //requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        try {
            userRepository.save(requestDTO.toEntity());
            findAll();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void findAll(){
        List <User> all = userRepository.findAll();
        for(User user: all){
            user.output();
        }
    }
}
