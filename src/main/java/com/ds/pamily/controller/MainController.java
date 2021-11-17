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

    @PreAuthorize("permitAll()")
    @GetMapping("/main")
    public void mainList(PageRequestDTO pageRequestDTO, Model model, PostDTO postDTO,@AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        log.info("PostDTO:>>>>> " + postDTO);
        model.addAttribute("msg" + postService.getList(pageRequestDTO));
        log.info( "model >>>>>>" + model );
        log.info(pageRequestDTO.getPage());
        log.info("pageRequestDTO?>>>>>: " + pageRequestDTO);
        log.info("PostDTO:>>>>> " + postDTO);
        model.addAttribute("nickname", authMemberDTO.getName());
    }
}
