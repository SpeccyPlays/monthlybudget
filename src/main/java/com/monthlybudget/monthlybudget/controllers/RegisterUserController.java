package com.monthlybudget.monthlybudget.controllers;

import com.monthlybudget.monthlybudget.models.User;
import com.monthlybudget.monthlybudget.models.User.Role;
import com.monthlybudget.monthlybudget.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegisterUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("username") String userName,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes,
            Model model) {
        User newUser = userService.findByUsername(userName);
        if (newUser != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        newUser = new User();
        newUser.setUsername(userName);
        newUser.setPassword(password);
        newUser.setRole(Role.user);
        if (!userService.saveUser(newUser)){
            model.addAttribute("error", "Error when registering user");
            return "register";
        };
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please log in.");
        return "redirect:/budgetpage";
    }

}
