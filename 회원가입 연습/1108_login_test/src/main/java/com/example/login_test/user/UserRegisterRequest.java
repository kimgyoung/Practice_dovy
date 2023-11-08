package com.example.login_test.user;

import com.example.login_test.kakao.KakaoUserInforDto;
import lombok.Getter;

@Getter
public class UserRegisterRequest extends KakaoUserInforDto {
    private String password;
    private String phoneNumber;

    public UserRegisterRequest(String nickname, String email, String profileImage) {
        super(nickname, email, profileImage);
    }
}
