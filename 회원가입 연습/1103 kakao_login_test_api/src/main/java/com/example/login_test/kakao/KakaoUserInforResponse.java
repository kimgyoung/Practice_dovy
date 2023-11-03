package com.example.login_test.kakao;

import lombok.*;

@Getter
@AllArgsConstructor
public class KakaoUserInforResponse {
    private final long id;
    private final Properties properties;
    private final KakaoAccount kakao_account;

    @Getter
    @AllArgsConstructor
    public static class Properties {
        private final String nickname;
        private final String profile_image;
        private final String thumbnail_image;
    }

    @Getter
    @AllArgsConstructor
    public static class KakaoAccount {
        private final String email;
    }
}
