package com.monthlybudget.monthlybudget.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
