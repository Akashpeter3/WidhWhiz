package com.app.eureka.server.eurekaserverapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
 @GetMapping("/login")
 public String login(Model model, String logout, String expired) {
     if (logout != null) {
         model.addAttribute("message", "You have been logged out.");
     }
     if (expired != null) {
         model.addAttribute("message", "Your session has expired. Please login again.");
     }
     
     return "login"; // Points to login.html (Thymeleaf template)
 }
    
}
