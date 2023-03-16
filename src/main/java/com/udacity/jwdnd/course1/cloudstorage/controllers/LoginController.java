package com.udacity.jwdnd.course1.cloudstorage.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage(@ModelAttribute("status") String status, Model model) {
        log.info("login getLoginPage");
        if (status.equals("success")) {
            model.addAttribute("success", true);
            return "login";
        }
        return "login";
    }

}
