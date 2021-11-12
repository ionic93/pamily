package com.ds.pamily.controller;

import com.ds.pamily.dto.MemberDTO;
import com.ds.pamily.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/join/")
public class JoinController {
    private final MemberServiceImpl memberServiceimpl;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("permitAll()")
    @GetMapping("/signUp")
    public void exsignUp() {
        log.info("exsignUp!");
    }


    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public void formSignUp(@ModelAttribute MemberDTO memberDTO,
                           HttpServletResponse response) throws Exception {
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        Long reg = memberServiceimpl.register(memberDTO);
        log.info("formSignUp!");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (reg == 0) {
            out.println("<script>alert('이미 가입된 계정입니다.'); </script>");
            out.println("<script> location.href='/pamily' </script>");
        } else {
            out.println("<script>alert('가입이 완료되었습니다.'); </script>");
            out.println("<script> location.href='/pamily/sample/main' </script>");
        }
        out.flush();
    }

}
