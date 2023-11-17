package com.example.login_test.kakao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByAccessToken(String accessToken);
    List<Token> findByUserId(String userId);
    void deleteByUserId(String userId);
}

