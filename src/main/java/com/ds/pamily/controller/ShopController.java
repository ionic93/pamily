package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.dto.ShopImageDTO;
import com.ds.pamily.security.dto.AuthMemberDTO;
import com.ds.pamily.service.ShopCateService;
import com.ds.pamily.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/shop/")
public class ShopController {
    private final ShopService shopService;
    private final ShopCateService shopCateService;

    @GetMapping({"/read","/modify"})
    public void shopRead(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long sid, Model model) {
        log.info("sid: "+ sid);
        ShopDTO shopDTO = shopService.getShop(sid);
        log.info(shopDTO);
        model.addAttribute("category",shopCateService.getCateList());
        model.addAttribute("authMid", authMemberDTO.getMid());
        model.addAttribute("authName", authMemberDTO.getName());
        model.addAttribute("dto",shopDTO);
    }

    @PostMapping("/modify")
    public String shopModify(ShopDTO shopDTO, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("shop modify.......................................");
        log.info("modShop: "+shopDTO);

        shopService.modify(shopDTO);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("scno",requestDTO.getScno());

        redirectAttributes.addAttribute("sid",shopDTO.getSid());
        return "redirect:/shop/read";
    }



    @PostMapping("/remove")
    public String remove(long sid, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("deletesid: "+ sid);
        shopService.removeWithShopImageAndReply(sid);
        redirectAttributes.addFlashAttribute("msg",sid);
        redirectAttributes.addFlashAttribute("noti","??????");
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("scno",requestDTO.getScno());
        return "redirect:/shop/shop";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shop")
    public void exshop(PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("exitemshop..........");
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("category",shopCateService.getCateList());
        model.addAttribute("nickname", authMemberDTO.getName());
        model.addAttribute("result", shopService.getList(pageRequestDTO));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shopreg")
    public void exshopReg(ShopDTO shopDTO, @AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {

        model.addAttribute("category",shopCateService.getCateList());
        model.addAttribute("mid",authMemberDTO.getMid());
        model.addAttribute("Authname",authMemberDTO.getName());
        log.info("ShopReg...");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/shopreg")
    public String exshopRegPost(ShopDTO shopDTO, RedirectAttributes redirectAttributes) {
        log.info("shopDTO>>"+shopDTO);
        Long sid = shopService.shopRegister(shopDTO);
        redirectAttributes.addFlashAttribute("msg",sid);
        log.info("getShopImageDTOList..."+shopDTO.getShopImageDTOList());
        return "redirect:/shop/shop";
    }
}
