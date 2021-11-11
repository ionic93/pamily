package com.ds.pamily.controller;

import com.ds.pamily.dto.MemberDTO;
import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.repository.MemberRepository;
import com.ds.pamily.security.dto.AuthMemberDTO;
import com.ds.pamily.service.MemberService;
import com.ds.pamily.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class PostController {
    private final PostService postService;
    private final MemberRepository memberRepository;

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/uploadPage")
//    public void exuploadPage(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
//        log.info("uploadPage.......");
//        log.info("AuthMemberDTO"+authMemberDTO);
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @PostMapping("/uploadPage") //등록후
//    public String register(PostDTO postDTO, RedirectAttributes redirectAttributes){
////        redirect하는 순간 기존 요청은 끊어지고 새로운 GET 요청을 시작함
//        //그렇기에 RedirectAttributes를 사용해 redirect하기 전 Session에 Flash속성을 복사한후
//        //저장된 플래시 속성을 Model로 이동시킨다.
//        log.info("postDTO: "+postDTO);
//
//        Long pid = postService.register(postDTO);
//        redirectAttributes.addFlashAttribute("msg",pid);
//        return "redirect:/sample/main";
//    }

//    @PreAuthorize("hasRole('USER')")
    @PreAuthorize("permitAll()")
    @GetMapping("/uploadPage")
    public void register(Principal principal) {
        log.info("principal>>"+principal.getName());
        log.info("role>>");
        log.info("uploadPage..........");
    }

//    @PreAuthorize("hasRole('USER')")
    @PreAuthorize("permitAll()")
    @PostMapping("/uploadPage")
    public String register(PostDTO postDTO, RedirectAttributes redirectAttributes){
        Long pid = postService.register(postDTO);
        redirectAttributes.addFlashAttribute("msg", pid);
        log.info("postDTOpostDTO:>> " + postDTO);
        return "redirect:/sample/homePage";
    }

}
