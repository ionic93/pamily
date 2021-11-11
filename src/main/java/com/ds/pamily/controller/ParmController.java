package com.ds.pamily.controller;

import com.ds.pamily.service.ParmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/sample/myParm")
public class ParmController {

    private final ParmService parmService;

    @GetMapping("")
    public void index() {

    }



}
