package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.ShopCate;
import com.ds.pamily.service.ShopCateService;
import com.ds.pamily.service.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/shop/")
public class ShopController {
    private final ShopService shopService;
    private final ShopCateService shopCateService;

    @PreAuthorize("permitAll()")
    @GetMapping("/shop")
    public void exshop(PageRequestDTO pageRequestDTO, Model model) {
        log.info("exitemshop..........");
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", shopService.getList(pageRequestDTO));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/shopreg")
    public void exshopReg() {
        log.info("ShopReg...");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/shopreg")
    public String exshopRegPost(ShopDTO shopDTO, RedirectAttributes redirectAttributes) {
        log.info("shopDTO..."+shopDTO);
        Long sid = shopService.shopRegister(shopDTO);
        redirectAttributes.addFlashAttribute("msg",sid);
        return "redirect:/exshop";
    }

}
