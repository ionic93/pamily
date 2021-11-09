package com.ds.pamily.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample/")
public class MyProfileController {
    @PreAuthorize("permitAll()")
    @GetMapping("/myProfile")
    public void exProfile() {
        log.info("exProfile..........");
    }

}
