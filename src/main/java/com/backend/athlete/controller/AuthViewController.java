package com.backend.athlete.controller;

import com.backend.athlete.application.AuthService;
import com.backend.athlete.presentation.user.request.LoginTokenRequest;
import com.backend.athlete.presentation.user.request.RegisterUserRequest;
import com.backend.athlete.presentation.user.response.LoginTokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthViewController {

    private final AuthService authService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("pageTitle", "회원가입");
        return "user/register";
    }
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterUserRequest request) {
        authService.register(request);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("pageTitle", "회원가입");
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginTokenRequest request, HttpServletResponse response) {
        LoginTokenResponse jwtToken = authService.login(request);
        authService.addJwtCookie(response, jwtToken);
        return "redirect:/main";
    }

}
