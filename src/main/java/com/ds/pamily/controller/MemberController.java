package com.ds.pamily.controller;

import com.ds.pamily.security.service.PamilyUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {
    private final PamilyUserDetailsService pamilyUserDetailsService;
    private final PasswordEncoder passwordEncoder;

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

    @PreAuthorize("permitAll()")
    @GetMapping("/updateMInfo")
    public void updateMInfo() {
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
