package com.udacity.jwdnd.course1.cloudstorage.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/result")
public class ResultController {
    @GetMapping
    public String getResultPage(@RequestParam("status") String status, @ModelAttribute("errorMessage") String errorMessage, Model model) {
        log.info("result getResultPage");
        if (status.equals("success")) {
            model.addAttribute("success", true);
        } else if (status.equals("error")) {
            model.addAttribute("error", errorMessage);
        }
        return "result";
    }
}
