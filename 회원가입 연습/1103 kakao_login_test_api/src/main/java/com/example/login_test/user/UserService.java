package com.example.login_test.user;

import com.example.login_test.error.Exception400;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public void join(UserRequest.JoinDto requestDto){
        checkEmail(requestDto.getEmail());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(encodedPassword);

        userRepository.save(requestDto.toEntity());
    }

    public String login(UserRequest.JoinDto requestDto){


        return null;
    }


    public void checkEmail(String email){
        Optional<User> users = userRepository.findByEmail(email);
        if(users.isPresent()){
            throw new Exception400("이미 존재하는 이메일 입니다.: " + email);
        }
    }
}
