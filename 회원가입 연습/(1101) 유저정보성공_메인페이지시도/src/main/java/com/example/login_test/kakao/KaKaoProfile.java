package com.example.login_test.kakao;

import lombok.Getter;

@Getter
public class KaKaoProfile {
    private Long id;
    private Properties properties;

    @Getter
    public static class Properties{
        private String nickname;
    }
}
