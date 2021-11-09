package com.ds.pamily.controller;

import com.ds.pamily.entity.Item;
import com.ds.pamily.repository.ItemRepository;
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
@RequestMapping("/sample/")
public class ParmShopController {
    final ItemRepository itemRepository;

    @PreAuthorize("permitAll()")
    @GetMapping("/itemshop")
    public String exItemShop(Model model){
        List<Item> itemList = itemRepository.findAll();
        model.addAttribute("itemList", itemList);
        log.info("item>>>>>>" + itemList.size());
        log.info("exitemshop..........");
        return "sample/itemshop";
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
