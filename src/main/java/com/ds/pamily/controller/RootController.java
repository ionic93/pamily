package com.ds.pamily.controller;

import com.ds.pamily.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class RootController {
    @PreAuthorize("permitAll()")
    @GetMapping({"","/"})
    public String exmainPage(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        return "redirect:/member/login";
    }

}
