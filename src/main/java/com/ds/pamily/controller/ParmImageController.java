package com.ds.pamily.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

@Controller
@RequestMapping("/sample/myParm")
public class ParmImageController {

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String parm(Locale locale, Model model) {
//        model.addAttribute("parmImage", "assets/img/parm.png");
//        model.addAttribute("item", "exItemPicture");
//        return "/sample/myParm";
//    }
}
