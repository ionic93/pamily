package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.ShopCateDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.service.ShopCateService;
import com.ds.pamily.service.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/shop/")
public class ShopController {

    private final ShopCateService shopCateService;
    private final ShopService shopService;

    @PreAuthorize("permitAll()")
    @GetMapping("/shop")
    public String exItemShop(PageRequestDTO pageRequestDTO, Model model) throws JsonProcessingException {
        List<ShopCateDTO> cateDTOS = shopCateService.getCate();
        model.addAttribute(("cateDTOS"), cateDTOS);
        List<ShopDTO> shopDTOS = shopService.getShop();
        model.addAttribute(("shopDTOS"), shopDTOS);
        log.info("shopDTOS >>>>>>>>>"+shopDTOS);

        model.addAttribute("result", shopService.getShopList(pageRequestDTO));

        log.info("exitemshop..........");
        return "shop/shop";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/itempay")
    public void exItemPay() {
        log.info("exitempay..........");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/itemshopM")
    public void exitemshopM() {
        log.info("exitemshopM..........");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/itemshopO")
    public void exitemshopO() {
        log.info("exitemshopO..........");
    }


}
