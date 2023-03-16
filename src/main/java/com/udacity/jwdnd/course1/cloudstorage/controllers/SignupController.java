package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignUpPage() {
        log.info("signup getSignUpPage");
        return "signup";
    }

    @PostMapping
    public String postSignUpPage(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model) {
        log.info("signup postSignUpPage");
        if (!userService.isUsernameUnAvailable(user.getUsername())) {
            model.addAttribute("error", "Username already exists. Please try other username.");
        } else {
            int userAdded = userService.createUser(user);
            if (userAdded < 0) {
                model.addAttribute("error", "An error occurs. Please try again.");
            } else {
                redirectAttributes.addFlashAttribute("status", "success");
                return "redirect:/login";
            }
        }
        return "signup";
    }
}
