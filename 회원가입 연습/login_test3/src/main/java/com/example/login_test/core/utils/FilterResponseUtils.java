package com.example.login_test.core.utils;

import com.example.login_test.core.error.exception.Exception401;
import com.example.login_test.core.error.exception.Exception403;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterResponseUtils {
    public static void unAuthorized(HttpServletResponse response, Exception401 e) throws IOException {

        // 예외 처리가 들어온 것을 클라이언트에게 보내겠따는 의미
        // json 보낼 수 있는 형태로 설정해줌
        // json을 문자열로 바꿔 줌

        // ** Http 응답 설정
        response.setStatus(e.status().value());
        response.setContentType("application/json; charset=utf-8");

        // ** json 을 문자열로 변경 후 응답
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(e.body());
        response.getWriter().println(responseBody);

    }

    public static void forbidden(HttpServletResponse response, Exception403 e) throws IOException{
        response.setStatus(e.status().value());
        response.setContentType("application/json; charset=utf-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(e.body());
        response.getWriter().println(responseBody);
    }
}
