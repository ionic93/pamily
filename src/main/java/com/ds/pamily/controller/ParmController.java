package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.ParmDTO;
import com.ds.pamily.service.ParmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/sample/myParm")
public class ParmController {

    private final ParmService service;

    @GetMapping("")
    public void index() {

    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list......." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(ParmDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        Long fno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", fno);
        return "redirect:/sample/list";
    }
}
