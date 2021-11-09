package com.ds.pamily.controller;

import com.ds.pamily.dto.MemberDTO;
import com.ds.pamily.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class RegisterController {
    private final MemberServiceImpl memberServiceimpl;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("permitAll()")
    @GetMapping("/signUp")
    public void exsignUp() {
        log.info("exsignUp!");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    @RequestMapping(value = "/session-over", method = { RequestMethod.POST, RequestMethod.GET })
    public String formSignUp(@ModelAttribute MemberDTO memberDTO,
                             HttpServletResponse response) throws Exception {
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        Long reg = memberServiceimpl.register(memberDTO);
        log.info("formSignUp!");
        if (reg == 0) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('이미 가입된 계정입니다.');</script>");
            out.flush();
            return "redirect:/sample/pamily";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('가입이 완료되었습니다.');</script>");
            out.flush();
            return "redirect:/sample/all";
        }
    }

    @GetMapping("/index")
    public void login(String error, String logout, Model model) {
        log.info("login error: " + error);
        log.info("login logout: " + logout);
        if (error != null) {
            model.addAttribute("error", "Login Error Check Your Account");
        }
        if (logout != null) {
            model.addAttribute("logout", "Logout");
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/findPass")
    public void exfindPass() {
        log.info("exPass~");
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/updateMInfo")
    public void updateMInfo() {
        log.info("updateMInfo~");
    }
}
