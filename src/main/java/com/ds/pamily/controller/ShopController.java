package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.security.dto.AuthMemberDTO;
import com.ds.pamily.service.ShopCateService;
import com.ds.pamily.service.ShopService;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shopreg")
    public void exshopReg(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        String name = authMemberDTO.getName();
        model.addAttribute("name",name);
        log.info("ShopReg...");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/shopreg")
    public String exshopRegPost(ShopDTO shopDTO, RedirectAttributes redirectAttributes, PageRequestDTO pageRequestDTO, Model model) {
        log.info("shopDTO..."+shopDTO);
        Long sid = shopService.shopRegister(shopDTO);
        redirectAttributes.addFlashAttribute("msg",sid);
        return "redirect:/shop";
    }
}
