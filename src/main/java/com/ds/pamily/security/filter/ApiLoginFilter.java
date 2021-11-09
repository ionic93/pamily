package com.ds.pamily.security.filter;

import com.ds.pamily.security.dto.AuthMemberDTO;
import com.ds.pamily.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
//특정한 URL로 외부에서 로그인이 가능하도록 하고
//로그인 성공시 클라이언트가 Authorization 헤더의 값으로 이용할 데이터를 전송
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
    private JWTUtil jwtUtil;

    //JWTUtil을 생성자를 통해 주입받는 구조
    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
            super(defaultFilterProcessesUrl);
            this.jwtUtil = jwtUtil;
        }

    @Override
    //인증 시도
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("-----------ApiLoginFilter--------------");
        log.info("attemptAuthentication");

        String email = request.getParameter("email");
        String pw = request.getParameter("pw");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pw);

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    //인증 성공
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("-----------ApiLoginFilter--------------");
        log.info("successfulAuthentication"+authResult);

        log.info(authResult.getPrincipal());

        //email address
        String email = ((AuthMemberDTO)authResult.getPrincipal()).getUsername();

        String token = null;

        try {
            token = jwtUtil.generateToken(email);

            response.setContentType("text/plain");
            // 클라이언트에 token을 문자열로 전송
            response.getOutputStream().write(token.getBytes());

            log.info("token: "+token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
