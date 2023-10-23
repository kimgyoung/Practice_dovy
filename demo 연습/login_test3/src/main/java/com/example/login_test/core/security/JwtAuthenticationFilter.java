package com.example.login_test.core.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.login_test.core.error.exception.Exception404;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String prefixJwt = request.getHeader(JwtTokenProvider.HEADER);

        // 헤더가 없으니까 그냥 넘어가 아무일X
        // ** 헤더가 없다면 더이상 이 메서드에서 할 일은 없음. 다음으로 넘김
        if(prefixJwt == null){
            chain.doFilter(request, response);
            return;
        }

        // 이미 넘어 왔을 땐 Bearer을 넘겨
        // ** Bearer 제거
        // 토큰만 남겨야 하니까..
        String jwt = prefixJwt.replace(JwtTokenProvider.TOKEN_PREFIX, "");

        try {
            log.debug("토큰 있음");

            // ** 토큰 검증
            //DecodedJWT decodedJWT = JwtTokenProvider.verify(jwt);

        }catch (AuthenticationException ex){
            //throws
        }


        // 시스템.out 출력과 다른게 실제로 파일로 전환 해서 사용 가능
        // 나중에 로그 확인 가능
        log.debug("");


        try {

        }catch (AuthenticationException ex){

        }
        chain.doFilter(request, response);
    }

}
