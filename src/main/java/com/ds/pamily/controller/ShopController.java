package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.ShopCateDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shopreg")
    public void exshopReg(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        String name = authMemberDTO.getName();
        model.addAttribute("name",name);
        List<ShopCateDTO> cateDTOS = shopCateService.getCate();
        model.addAttribute(("cateDTOS"), cateDTOS);
        log.info("cateDTOS>>>>>"+cateDTOS);
        log.info("ShopReg...");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/shopreg")
    public String exshopRegPost(ShopDTO shopDTO, RedirectAttributes redirectAttributes
                    , PageRequestDTO pageRequestDTO, Model model, @RequestParam("file") List<MultipartFile> files) {
        log.info("shopDTO..."+shopDTO);
        log.info("files>>>>>" + files);
        Long sid = shopService.shopRegister(shopDTO);
        redirectAttributes.addFlashAttribute("msg",sid);
        return "redirect:/shop";
    }

    @GetMapping({"/shopread", "/shopmodi"})
    public void read(Long sid, Model model,
                     @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
        ShopDTO shopDTO = shopService.read(sid);
        model.addAttribute("shopDTO", shopDTO);
    }
    @PostMapping("/shopmodi")
    public String modify(ShopDTO shopDTO, RedirectAttributes redirectAttributes,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
        shopService.modify(shopDTO);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("sid", shopDTO.getSid());
        return "redirect:/shop/shopread";
    }

    @PostMapping("/remove")
    public String remove(Long sid, RedirectAttributes redirectAttributes,
                         PageRequestDTO pageRequestDTO) {
        shopService.remove(sid);
        redirectAttributes.addFlashAttribute("msg", sid);
        redirectAttributes.addFlashAttribute("noti", "삭제");
        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type", pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/shop/shop";
    }


}
