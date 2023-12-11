package com.example.product.error.exception;

import com.example.product.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.NOT_FOUND);
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}
