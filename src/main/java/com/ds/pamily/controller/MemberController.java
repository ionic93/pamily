package com.ds.pamily.controller;

import com.ds.pamily.dto.MemberDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.security.dto.AuthMemberDTO;
import com.ds.pamily.security.service.PamilyUserDetailsService;
import com.ds.pamily.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.System.out;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {
    private final PamilyUserDetailsService pamilyUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final MemberServiceImpl memberService;

    @GetMapping("/login")
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
    public void findPass() {
        log.info("findMyPass!");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/findPass")
    public void findPass(MemberDTO findMemberDTO, HttpServletResponse response) throws IOException {
        log.info("findMyPass!");
        log.info("updateMInfo~");
        log.info("findMemberDTO>>" + findMemberDTO);
        MemberDTO originMember = memberService.get(findMemberDTO.getEmail());
        log.info("originMember>>" + originMember);


        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (originMember.getMobile().equals(findMemberDTO.getMobile()) && originMember.getEmail().equals(findMemberDTO.getEmail())) {
            out.println("<script>alert('확인되었습니다.');</script>");
            out.println("<script> location.href='/pamily/member/newPass?email=" + originMember.getEmail() + "'; </script>");
        } else {
            out.println("<script>alert('가입되지 않은 이메일입니다.');</script>");
            out.println("<script> location.href='/pamily/member/findPass'; </script>");
        }
        out.flush();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/newPass")
    public void newPass(String email, @AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        log.info("New Pass");
        model.addAttribute("email", email);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/newPass")
    public void newPass(MemberDTO newMInfo, HttpServletResponse response) throws IOException {

        log.info("New Pass newMInfo>>" + newMInfo);
        MemberDTO beforeMInfo = memberService.get(newMInfo.getEmail());
        beforeMInfo.setPassword(newMInfo.getPassword());
        beforeMInfo.setPassword(passwordEncoder.encode(beforeMInfo.getPassword()));
        memberService.modify(beforeMInfo);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('변경되었습니다.');</script>");
        out.println("<script> location.href='/pamily/member/login; </script>");
        out.flush();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/updateMInfo")
    public void updateMInfo(MemberDTO memberDTO, Model model, @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("g:" + authMemberDTO);
        model.addAttribute("mid", authMemberDTO.getMid());
        model.addAttribute("email", authMemberDTO.getEmail());
        model.addAttribute("nickname", authMemberDTO.getName());
        model.addAttribute("mobile", authMemberDTO.getMobile());
        model.addAttribute("password", authMemberDTO.getPassword());
        model.addAttribute("check-password", authMemberDTO.getPassword());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/updateMInfo")
    public void updateMInfo(MemberDTO changeMInfo, @AuthenticationPrincipal AuthMemberDTO authMemberDTO, HttpServletResponse response) throws IOException {
        log.info("updateMInfo~");

        MemberDTO beforeMInfo = memberService.get(authMemberDTO.getUsername());
        if (!changeMInfo.getPassword().equals("")) {
            changeMInfo.setPassword(passwordEncoder.encode(changeMInfo.getPassword()));
        } else {
            changeMInfo.setPassword(beforeMInfo.getPassword());
        }
        memberService.modify(changeMInfo);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (!beforeMInfo.equals(changeMInfo)) {
            out.println("<script>alert('변경되었습니다.');</script>");
            out.println("<script> location.href='/pamily/sample/main'; </script>");
        } else {
            out.println("<script>alert('정보 변경이 취소되었습니다.');</script>");
            out.println("<script> location.href='/pamily/sample/main'; </script>");
        }
        out.flush();
    }

//    @PostMapping("/login")
//    public String loginForm(String email, String password) {
//        String resultUrl = "";
//        log.info("custom login..........");
//        log.info("email: "+email);
//        log.info("password: "+password);
//        AuthMemberDTO authMemberDTO = (AuthMemberDTO) pamilyUserDetailsService.loadUserByUsername(email);
//        log.info("authMemberDTO: " + authMemberDTO.getAuthorities());
//        log.info("pamilyAuthMemberDTO: "+ authMemberDTO);
//
//        boolean passwordResult = passwordEncoder.matches(password, authMemberDTO.getPassword());
//        if (passwordResult) {
//            log.info("passwordMatch");
//            resultUrl = "redirect:/sample/main";
//        } else {
//            log.info("passwordMissMatch");
//            resultUrl = "redirect:/sample/index";
//        }
//        return resultUrl;
//    }
}
