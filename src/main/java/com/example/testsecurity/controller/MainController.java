package com.example.testsecurity.controller;

import com.example.testsecurity.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String mainP(Model model) {

        String name = mainService.checkName();
        String role = mainService.checkRole();

        model.addAttribute("name", name);
        model.addAttribute("role", role);

        return "main";
    }
}
