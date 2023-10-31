package com.example.login_test.kakao;

public class MsgEntity {
    private String status;
    private String accessToken;

    public MsgEntity(String status, String accessToken) {
        this.status = status;
        this.accessToken = accessToken;
    }

    public String getStatus() {
        return status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "MsgEntity{" +
                "status='" + status + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
