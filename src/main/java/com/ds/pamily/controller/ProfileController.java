package com.ds.pamily.controller;

import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class ProfileController {
    private final ProfileService profileService;

    @PreAuthorize("permitAll()")
    @GetMapping("/profileModify")
    public void register(Principal principal) {
        log.info("principal>>"+principal.getName());
        log.info("role>>");
        log.info("uploadPage..........");
    }

//    @PreAuthorize("permitAll()")
//    @PostMapping("/profileModify")
//    public String register(PostDTO postDTO, RedirectAttributes redirectAttributes){
//        Long pid = postService.register(postDTO);
//        redirectAttributes.addFlashAttribute("msg", pid);
//        log.info("postDTOpostDTO:>> " + postDTO);
//        return "redirect:/sample/main";
//    }

}
