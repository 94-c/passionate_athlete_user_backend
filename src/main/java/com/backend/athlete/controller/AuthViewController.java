package com.backend.athlete.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthViewController {
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("pageTitle", "회원가입");
        return "user/register";
    }
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("pageTitle", "로그인");
        return "login";
    }
}
