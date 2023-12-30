package com.chatsyncapp.chatSyncApp.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("page", "home");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        model.addAttribute("messages", list);
        return "index";
    }

    @GetMapping("/contacts")
    public String contacts(Model model){
        model.addAttribute("page", "contacts");
        model.addAttribute("message", "Hello, Contacts!");
        return "contacts";
    }

    @GetMapping("/groups")
    public String groups(Model model){
        model.addAttribute("page", "groups");
        model.addAttribute("message", "Hello, Groups!");
        return "groups";
    }

    @GetMapping("/settings")
    public String settings(Model model){
        model.addAttribute("page", "settings");
        model.addAttribute("message", "Hello, Settings!");
        return "settings";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        model.addAttribute("page", "profile");
        model.addAttribute("message", "Hello, Profile!");
        return "profile";
    }

}
