package com.example.login_test.core.error.exception;

import com.example.login_test.core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class Exception403 extends RuntimeException{

    public Exception403(String message){
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.FORBIDDEN);
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }

}
