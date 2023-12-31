package com.example.login_test.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    // 밑에 건 확인 출력용
    Optional<User> findByEmailAndIsKakaoUserTrue(String email);
}

