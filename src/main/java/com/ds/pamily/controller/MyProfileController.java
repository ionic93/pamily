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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class MyProfileController {
    private final PostService postService;
    private final ProfileService profileService;

    @PreAuthorize("permitAll()")
    @GetMapping("/myProfile")
    public void exProfile(PageRequestDTO pageRequestDTO, Model model) {
        PageResultDTO<PostDTO, Object[]> result = postService.getList(pageRequestDTO);
        log.info("result.getDtoList()>>>>>>>>>>>" + result.getDtoList());
        model.addAttribute("msg" , result.getDtoList());
        log.info( "model >>>>>>" + model );
        log.info( "result1>>>>>>" + result );
        log.info("result2>>>>>>>>" + result.getDtoList());

        PageResultDTO<ProfileDTO, Object[]> imageResult = profileService.getList(pageRequestDTO);
        log.info("imageResult.getDtoList()>>>>" + imageResult.getDtoList());
        model.addAttribute("imageResult",imageResult.getDtoList().get(imageResult.getSize() - result.getSize()).getProfileImageDTOList());

        log.info("lastImage>>>>>>>>>" + imageResult.getDtoList().get(imageResult.getSize() - result.getSize()).getProfileImageDTOList());

        log.info(model);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/profileModify")
    public void register(Principal principal){
        log.info("principal>>"+principal.getName());
        log.info("role>>");
        log.info("uploadPage..........");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/profileModify")
    public String register(ProfileDTO profileDTO, RedirectAttributes redirectAttributes){
        Long profileId = profileService.register(profileDTO);
        redirectAttributes.addFlashAttribute("msg", profileId);
        log.info("profileDTO:>>>> " + profileDTO);
        return "redirect:/sample/myProfile";

    }
}
