package com.example.login_test.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity

@Table(name="user_tb_2")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = false)
    private String password;

    @Column(length = 45, nullable = false)
    private String username;

    // length 10 -> 아니고 11? 인지 나중에 가입 하면서 확인
    @Column(length = 10, nullable = false)
    private String phoneNumber;


    @Column(length = 30)
    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();


    @Builder
    public User(int id, String email, String password, String username, String phoneNumber, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}

/*
기존 유저 회원가입

일단 회원가입이 되어있는지 확인부터 (체크부터 전화번호나 이메일로 확인)
-> 하고 카카오 로그인 or join 로직 실행

+ 카카오 email값으로  회원가입 구현
+ 회원가입 로그아웃, 카카오 로그아웃

+ 저장된 카카오 값 활용 ? DB에 저장
(원래는 DB도 두개임 원 User DB | 카카오 user DB)

+ ? 비어 있을 수 있는 상태 에서 가입로직? || not null?

+ 날씨 api, sendMassage?
 */