package com.chatsyncapp.chatSyncApp.webcontroller;

import com.chatsyncapp.chatSyncApp.service.impl.ContactsServiceImpl;
import com.chatsyncapp.chatSyncApp.service.impl.UserContactServiceImpl;
import com.chatsyncapp.chatSyncApp.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String LOGGER_TAG = "WebController : ";
    Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private ContactsServiceImpl contactsServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserContactServiceImpl userContactService;

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
        String userId = this.userServiceImpl.getLoggedInUserId();
        logger.info("requested userId : " + userId);
        model.addAttribute("contacts", this.userContactService.getUserContacts(userId).getContacts());
        return "contacts";
    }

    @GetMapping("/groups")
    public String groups(Model model) {
        model.addAttribute("page", "groups");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        List<Integer> members = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            members.add(i);
        }
        model.addAttribute("groups", list);
        model.addAttribute("members", members);
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

    @GetMapping("/test-ws")
    public String testWSPage(){
        return "test-ws";
    }

}
