package com.example.product.error.exception;

import com.example.product.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class Exception401 extends RuntimeException{
    public Exception401(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
