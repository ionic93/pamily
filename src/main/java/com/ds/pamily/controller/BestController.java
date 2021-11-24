package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.dto.ProfileDTO;
import com.ds.pamily.service.PostService;
import com.ds.pamily.service.ProfileService;
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
public class BestController {
    private final PostService postService;
    private final ProfileService profileService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/best")
    public void exbest(PageRequestDTO pageRequestDTO, Model model) {
        PageResultDTO<PostDTO, Object[]> result = postService.getList(pageRequestDTO);
        log.info("result.getDtoList()>>>>>>>>>>>" + result.getDtoList());
        model.addAttribute("msg" , result.getDtoList());
        log.info("result2>>>>>>>>" + result.getDtoList());

        PageResultDTO<ProfileDTO, Object[]> imageResult = profileService.getList(pageRequestDTO);
        log.info("imageResult.getDtoList()>>>>" + imageResult.getDtoList());
        model.addAttribute("imageResult" , imageResult.getDtoList());
        log.info("imageResult>>>>>"+imageResult);

    }
}
