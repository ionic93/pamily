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

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {
    private final PamilyUserDetailsService pamilyUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final MemberServiceImpl memberService;

    @GetMapping("/login")
    public void login(String error, String logout, Model model){
        log.info("login error: "+error);
        log.info("login logout: "+logout);
        if (error != null) {
            model.addAttribute("error","Login Error Check Your Account");
        }
        if (logout != null) {
            model.addAttribute("logout","Logout");
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/findPass")
    public void findPass() {
        log.info("findMyPass!");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/updateMInfo")
    public void updateMInfo() {
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/updateMInfo")
    public void updateMInfo(MemberDTO memberDTO, @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        MemberDTO changeMInfo = memberService.get(authMemberDTO.getUsername());
        changeMInfo.setEmail(memberDTO.getEmail());
        changeMInfo.setName(memberDTO.getName());
        changeMInfo.setPassword(passwordEncoder.encode(changeMInfo.getPassword()));
        changeMInfo.setMobile(memberDTO.getMobile());
        memberService.modify(changeMInfo);


        log.info("updateMInfo~");
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
