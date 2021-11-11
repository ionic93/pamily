package com.ds.pamily.controller;

import com.ds.pamily.dto.ItemCateDTO;
import com.ds.pamily.dto.ItemDTO;
import com.ds.pamily.dto.ItemTypeDTO;
import com.ds.pamily.entity.ItemCate;
import com.ds.pamily.service.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ShopService shopService;

    @PreAuthorize("permitAll()")
    @GetMapping("/itemshop")
    public String exItemShop(Model model) throws JsonProcessingException {
//        model.addAttribute("itemTypes", itemTypes);
//        model.addAttribute("itemCates", itemCates);
//
//        log.info("item>>>>>>" + itemList.size());
        List<ItemTypeDTO> typeDTOS = shopService.getType();
        model.addAttribute(("typeDTOS"), typeDTOS);
        log.info("typeDTO >>>>>>>>>"+typeDTOS);

        List<ItemCateDTO> cateDTOS = shopService.getCate();
        ObjectMapper mapper = new ObjectMapper();
        String jsonText = mapper.writeValueAsString( cateDTOS );
        model.addAttribute("cateDTOS", jsonText);
//        log.info("cateDTO >>>>>>>>>"+cateDTOS);

        ItemDTO itemDTO = shopService.getItem();
        model.addAttribute("itemDTO", itemDTO);
        log.info("itemDTO >>>>>>>>>"+itemDTO);

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
