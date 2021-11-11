package com.ds.pamily.controller;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.ParmDTO;
import com.ds.pamily.entity.ItemCate;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.ParmImage;
import com.ds.pamily.service.MemberService;
import com.ds.pamily.service.ParmImageService;
import com.ds.pamily.service.ParmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/sample/myParm")
public class ParmController {

    private final ParmService parmService;

    @GetMapping("")
    public void index() {

    }



}
