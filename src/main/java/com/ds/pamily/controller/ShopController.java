package com.ds.pamily.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/shop/")
public class ShopController {

    @PreAuthorize("permitAll()")
    @GetMapping("/shop")
    public String exItemShop(Model model) throws JsonProcessingException {
//        model.addAttribute("itemTypes", itemTypes);
//        model.addAttribute("itemCates", itemCates);
//
//        log.info("item>>>>>>" + itemList.size());

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