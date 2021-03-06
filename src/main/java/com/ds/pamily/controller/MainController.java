package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.service.MemberService;
import com.ds.pamily.service.PostService;
import com.ds.pamily.service.RelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class MainController {
    @Autowired
    private final RelationService relationService;
    @Autowired
    private final PostService postService;

    @PreAuthorize("permitAll()")
    @GetMapping("/main")
    public void mainList(PageRequestDTO pageRequestDTO, Model model){
        PageResultDTO<PostDTO, Object[]> result = postService.getList(pageRequestDTO);
        log.info("result.getDtoList()>>>>>>>>>>>" + result.getDtoList());
        model.addAttribute("msg" , result.getDtoList());
        model.addAttribute("result", relationService.getFriendList(pageRequestDTO));
        log.info("result.getFriendList()>>>>>>>>>>>" + relationService.getFriendList(pageRequestDTO));
        log.info( "model >>>>>>" + model );
        log.info( "result1>>>>>>" + result );
        log.info("result2>>>>>>>>" + result.getDtoList());
    }


}
