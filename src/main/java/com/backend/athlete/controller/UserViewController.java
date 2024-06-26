package com.backend.athlete.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserViewController {

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("pageTitle", "메인");
        return "user/main";
    }

}
