package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final 키워드를 따라가면서 생성해줌 (final 유무를 확인하고 넣어줌)
@Service
public class UserService {

    private final UserRepository userRepository;

    public String findByEmail (String email){
        User user = userRepository.findByEmail(email);

        // 리턴 값이 있다면 마지막으로 실행되는 위치가 어디냐만 생각! 그 위치로 가서 리턴값이 전달이 됨
        // 리턴 값이 있으면 그 값을 가지고 가고 없으면 걍 감
        if(user == null){
            return "없는 사용자";
        }
        return "있는 사용자";
    }
}
