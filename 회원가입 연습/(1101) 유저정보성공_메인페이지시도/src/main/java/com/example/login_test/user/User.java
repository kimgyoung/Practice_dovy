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

    /*
    @Column(length = 30)
    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();


     */
    @Builder
    public User(int id, String email, String password, String username, String phoneNumber, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        //this.roles = roles;
    }

    // 빌더 어노테이션 사용하지 않으면 어케 되는지

}

/*
기존 유저 회원가입
+ 카카오 email값으로  회원가입 구현
+ 회원가입 로그아웃, 카카오 로그아웃


+ 날씨 api, sendMassage?
 */