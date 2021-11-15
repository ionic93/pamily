package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.dto.PostImageDTO;
import com.ds.pamily.repository.PostImageRepository;
import com.ds.pamily.repository.PostRepository;
import com.ds.pamily.security.dto.AuthMemberDTO;
import com.ds.pamily.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dom4j.rule.Mode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class MainController {
    private final PostService postService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/main")
    public void exmainPage(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        //@AuthenticationPrincipal : 컨트롤러에서 로그인된 사용자의 정보를 확인

        log.info("main..........");
        log.info("----------------------");
        log.info(authMemberDTO);
        model.addAttribute("mid",authMemberDTO.getMid());
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/main")
    public void mainList(PageRequestDTO pageRequestDTO, Model model){
        log.info("PageRequestDTO:>>>>> " + pageRequestDTO);
        model.addAttribute("result" + postService.getList(pageRequestDTO));

    }

}
