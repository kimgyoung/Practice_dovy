package com.example.login_test.kakao;

import lombok.Getter;

@Getter
public class MsgEntity {
    private final String status;
    private final String accessToken;
    private final String userInformation;

    public MsgEntity(String status, String accessToken, String userInformation) {
        this.status = status;
        this.accessToken = accessToken;
        this.userInformation = userInformation;
    }
}
