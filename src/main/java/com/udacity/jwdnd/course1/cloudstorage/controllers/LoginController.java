package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage(@ModelAttribute("status") String status, Model model){
        System.out.println("get login");
        System.out.println(status);
        if (status.equals("success")){
            model.addAttribute("success",true);
            return "login";
        }
        return "login";
    }

}
