package com.chatsyncapp.chatSyncApp.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String showLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String postLogin(){
        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        // Perform any additional logout actions here (if needed)
        return "redirect:/login?logout";
    }

    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "index";
    }
}
