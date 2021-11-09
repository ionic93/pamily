package com.ds.pamily.controller;

import com.ds.pamily.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class MainController {
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/main")
    public void exmainPage(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        //@AuthenticationPrincipal : 컨트롤러에서 로그인된 사용자의 정보를 확인

        log.info("main..........");
        log.info("----------------------");
        log.info(authMemberDTO);
        model.addAttribute("mid",authMemberDTO.getMid());
    }
}
