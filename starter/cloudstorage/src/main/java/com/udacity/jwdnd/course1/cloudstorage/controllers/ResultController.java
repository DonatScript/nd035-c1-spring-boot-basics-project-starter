package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result")
public class ResultController {

    @GetMapping
    public String displayMessage(Model model){
        model.addAttribute("resultStatus", "Error");
        model.addAttribute("resultMessage", "Test Msg");
        System.out.println("ResultController    ");
        return "result";
    }
}
