package com.ds.pamily.security.handler;

import com.ds.pamily.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    public LoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("--------------------------");
        log.info("onAuthenticationSuccess ");
        //로그인 정보
        AuthMemberDTO authMember = (AuthMemberDTO) authentication.getPrincipal();
        log.info("authMember"+authMember);

        boolean fromSocial = authMember.isFromSocial();
        log.info("Need Modify Member? "+fromSocial);

        boolean passwordResult = passwordEncoder.matches("1111",authMember.getPassword());
        if (fromSocial && passwordResult) {
            redirectStrategy.sendRedirect(request,response,"/sample/main?from=social");
            return;
        }
        //권한을 기준으로 url 넘겨주기
        List<String> roleList = new ArrayList<>();
        //람다식
//        authMember.getAuthorities().forEach(grantedAuthority -> roleList.add(grantedAuthority.getAuthority()));
        authMember.getAuthorities().forEach(new Consumer<GrantedAuthority>() {
            @Override
            public void accept(GrantedAuthority grantedAuthority) {
                roleList.add(grantedAuthority.getAuthority());
            }
        });
        log.info("getAuthorites: "+roleList);

        String sendUrl = "";
        if (roleList.contains("ROLE_USER")) sendUrl = "/sample/main";
        if (roleList.contains("ROLE_MANAGER")) sendUrl = "/sample/main";
        if (roleList.contains("ROLE_ADMIN")) sendUrl = "/sample/main";
        log.info("sendUrl: "+sendUrl);
        redirectStrategy.sendRedirect(request, response, sendUrl);
    }
}
