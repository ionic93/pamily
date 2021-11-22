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
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class MainController {

    @Autowired
    private final PostService postService;

    private final MemberService memberService;
    private final RelationService relationService;


    @PreAuthorize("permitAll()")
    @GetMapping("/main")
    public void mainList(PageRequestDTO pageRequestDTO, Model model){
        PageResultDTO<PostDTO, Object[]> result = postService.getList(pageRequestDTO);
        log.info("result.getDtoList()>>>>>>>>>>>" + result.getDtoList());
        model.addAttribute("msg" , result.getDtoList());
        log.info( "model >>>>>>" + model );
        log.info( "result1>>>>>>" + result );
        log.info("result2>>>>>>>>" + result.getDtoList());
    }

    @PostMapping("/member/relation/{id1}/{id2}")
    @ResponseBody
    public void relation(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        Member member1 = memberService.findById(id1).get();
        Member member2 = memberService.findById(id2).get();
        relationService.relation(member1, member2);
    }

    @PostMapping("/member/unRelation/{id1}/{id2}")
    @ResponseBody
    public void unRelation(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        Member member1 = memberService.findById(id1).get();
        Member member2 = memberService.findById(id2).get();
    }
}
