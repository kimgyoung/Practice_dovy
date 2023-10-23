package com.example.login_test.core.security;

import com.example.login_test.core.error.exception.Exception401;
import com.example.login_test.user.User;
import com.example.login_test.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 들어온 값이 이메일 임
    // 내가 보낸 이메일과 일치하는 값을 찾아서 던져 주겠다 (user 디테일에)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                ()-> new Exception401("인증 되지 않았습니다.")
        );
        return new CustomUserDetails(user);
    }
}
