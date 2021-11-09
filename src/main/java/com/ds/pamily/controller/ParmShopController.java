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
public class ParmShopController {

    @PreAuthorize("permitAll()")
    @GetMapping("/itemshop")
    public void exItemShop() {
        log.info("exitemshop..........");
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
