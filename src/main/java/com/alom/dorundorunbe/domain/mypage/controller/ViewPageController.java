package com.alom.dorundorunbe.domain.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewPageController {
    @GetMapping("/view")
    public String viewPage() {
        return "viewPage";
    }
}
