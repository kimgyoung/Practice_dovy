package com.example.login_test.kakao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class KakaoUserInfor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String kakaoUserEmail;
    private String profile_nickname;
    private String kakaoProfileImageUrl;

    private String kakaoAccessToken;

}
