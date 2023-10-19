package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {

    @Setter
    @Getter
    public static class LoginDTO{

        // 데이터가 비어있을 수 없는 상태
        @NotEmpty
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "유효한 이메일 주소 형식이 아닙니다.")
        private String email;
        // 설명:
        // ^[A-Za-z0-9+_.-]+: 이메일 주소의 로컬 파트(local part)를 나타내며, 영문자, 숫자, 특수문자(+, _, ., -)가 포함될 수 있음
        // @: 이메일 주소에서 로컬 파트와 도메인 파트(domain part)를 구분하는 기호
        // [A-Za-z0-9.-]+: 이메일 주소의 도메인 파트를 나타내며, 영문자, 숫자, 특수문자가 포함될 수 있음
        // $: 문자열의 끝을 나타냄

        @NotEmpty
        @Size(min = 8, max = 20, message = "8자 이상 20자 이내로 작성 가능합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$", message = "영문자, 숫자, 특수문자를 포함한 8자 이상 20자 이내로 작성해야 합니다.")
        private String password;
        // 설명:
        // (?=.*[A-Za-z]): 최소한 하나의 영문자가 포함 되어야 함
        // (?=.*\d): 최소한 하나의 숫자가 포함되어야 함
        // (?=.*[@$!%*#?&]): 최소한 하나의 특수문자(@$!%*#?&)가 포함 되어야 함
        // [A-Za-z\d@$!%*#?&]{8,20}: 영문자, 숫자 및 특수문자(@$!%*#?&)만 허용 하며, 총 길이는 8에서 20 사이여야 함

        public User toEntity(){
            return User.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }



}
