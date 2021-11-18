package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class MyProfileController {
    private final PostService postService;

    @PreAuthorize("permitAll()")
    @GetMapping("/myProfile")
    public void exProfile(PageRequestDTO pageRequestDTO, Model model) {
        PageResultDTO<PostDTO, Object[]> result = postService.getList(pageRequestDTO);
        log.info("result.getDtoList()>>>>>>>>>>>" + result.getDtoList());
        model.addAttribute("result" , result.getDtoList());
    }

}
