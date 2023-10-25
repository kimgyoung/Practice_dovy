package com.example.login_test.core.security;

import com.example.login_test.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    // 디테일 서비스에서 받아온 유저
    // 서비스에 있던 - 최초의 유저 값
    private final User user;

    // 메서드 구현 - 전체 메서드 구현

    // 이(GrantedAuthority) 형태로 권한 정보를 저장 한다.
    // 내부적으로 검증 단계를 거치기 위해
    // 검사하기 좋은 형태로 바꾸기 위해
    // 통째로 외운거 써도 무방..

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
