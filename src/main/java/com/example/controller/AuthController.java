package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        model.addAttribute("title", "Dashboard");
        model.addAttribute("username", authentication.getName());
        
        // Get session timeout info
        int sessionTimeout = session.getMaxInactiveInterval();
        model.addAttribute("sessionTimeout", sessionTimeout / 60); // Convert to minutes
        
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/index";
    }
}