package com.example.testsecurity.controller;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping("/join")
    public String joinP() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {

        System.err.println(joinDTO.getUsername());

        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
