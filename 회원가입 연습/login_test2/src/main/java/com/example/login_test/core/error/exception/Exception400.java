package com.example.login_test.core.error.exception;

import com.example.login_test.core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class Exception400 extends RuntimeException {

    // 생성자
    public Exception400(String message){
        super(message);
    }

    // 함수
    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }

}
