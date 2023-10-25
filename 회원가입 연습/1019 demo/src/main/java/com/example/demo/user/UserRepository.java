package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

// ** JpaRepository<User,Integer>: CRUD를 정의하지 않아도 사용할 수 있음
// ** 메서드 추가 정의도 가능
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

}
