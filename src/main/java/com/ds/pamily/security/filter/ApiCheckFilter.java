package com.ds.pamily.security.filter;

import com.ds.pamily.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
//OncePerRequestFilter : 추상 클래스로 제공되는 필터로 매번 동작하고 가장 마지막에 동작하는 기본 필터
public class ApiCheckFilter extends OncePerRequestFilter {
    private AntPathMatcher antPathMatcher;
    private String pattern; // /notes/**/*
    private JWTUtil jwtUtil;

    public ApiCheckFilter(String pattern, JWTUtil jwtUtil) {
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("REQUESTURI: "+ request.getRequestURI());
        boolean result =  antPathMatcher.match(pattern, request.getRequestURI());
        log.info("antPathMatcher.match(): "+result);

        if (result) {
            log.info("ApiCheckFilter.........................................");
            log.info("ApiCheckFilter.........................................");
            log.info("ApiCheckFilter.........................................");
            //클라이언트에서 전송한 Request에 포함된 Authorization 헤더 값을 검사
            boolean checkHeader = checkAuthHeader(request);
            if (checkHeader) {
                filterChain.doFilter(request, response);
                return;
            } else {
                // Authorization 헤더값이 없거나 다른 값을 전송할 경우 Json 포맷의 에러 메세지 전송
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                //joson 리턴 및 한글깨짐 수정
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", "403");
                json.put("message",message);

                PrintWriter out = response.getWriter();
                out.println(json);
                return;
            }
        }
       filterChain.doFilter(request, response); //다음 필터로 넘어가는 역할
    }

    private boolean checkAuthHeader(HttpServletRequest request) {
        boolean checkResult = false;
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")) {
            log.info("Authorization exist: "+authHeader);

            try {
                // Bearer(공백) 뒤부터 시작 = 7
                String email = jwtUtil.validateAndExtract(authHeader.substring(7));
                log.info("validate result: "+email);
                checkResult = email.length() > 0; //이메일의 길이값이 0보다 크므로 true가 됨
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return checkResult;
    }
}
