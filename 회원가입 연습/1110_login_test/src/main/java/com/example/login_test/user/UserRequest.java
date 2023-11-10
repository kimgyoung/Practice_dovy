package com.example.login_test.user;

import com.example.login_test.kakao.KakaoUserInforDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {

        // 데이터가 비어있을 수 없는 상태.
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 20, message = "8자 이상 20자 이내로 작성 가능합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;

        @NotEmpty
        private String username;

        private String phoneNumber;


        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .phoneNumber(phoneNumber)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
    }
    @Getter
    @Setter
    public static class KakaoJoinDTO {
        private String email;
        private String username;
        private String phoneNumber = "0000000000"; // Default phone number

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .username(username)
                    .phoneNumber(phoneNumber) // Set phone number
                    .roles(Collections.singletonList("ROLE_USER"))
                    .isKakaoUser(true)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class KakaoLoginDTO {
        private String email;
        private String phoneNumber = "0000000000"; // Default phone number

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .phoneNumber(phoneNumber) // Set phone number
                    .isKakaoUser(true)
                    .build();
        }
    }


}





