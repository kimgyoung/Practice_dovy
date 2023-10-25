package com.example.login_test.core.security;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.login_test.core.error.exception.Exception404;
import com.example.login_test.user.StringArrayConverter;
import com.example.login_test.user.User;
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
import java.util.List;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    // ** Http 요청이 발생 할 때마다 호출되는 메서드.
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
            // 시스템.out 출력과 다른게 실제로 파일로 전환 해서 사용 가능
            // 나중에 로그 확인 가능: log.debug("");
            log.debug("토큰 있음");

            // ** 토큰 검증
            DecodedJWT decodedJWT = JwtTokenProvider.verify(jwt);

            // ** 사용자 정보 추출
            int id = decodedJWT.getClaim("id").asInt();
            String roles = decodedJWT.getClaim("roles").asString();

            // ** 권한 정보를 문자열 리스트로 반환
            StringArrayConverter stringArrayConverter = new StringArrayConverter();
            List<String> rolesList = stringArrayConverter.convertToEntityAttribute(roles);

            // ** 추출한 정보로 유저를 생성
            User user = User.builder().id(id).roles(rolesList).build();
            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            // ** Spring Security 가 인증 정보를 관리 하는 데 사용.
            // 우리가 쓰는게 X 시큐리티가 쓰는 거
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    customUserDetails.getPassword(),
                    customUserDetails.getAuthorities()
            );

            // ** Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("인증 객체 생성");

        }catch (SignatureVerificationException sve){
            log.debug("토큰 검증 실패");

        } catch (TokenExpiredException tee){
            log.debug("토큰 사용 만료");

        } finally {
            // ** 필터로 응답 처리
            chain.doFilter(request, response);
        }
    }
}







