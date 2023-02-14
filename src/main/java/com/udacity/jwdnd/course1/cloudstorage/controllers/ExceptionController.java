package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@ControllerAdvice
public class ExceptionController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleException(MaxUploadSizeExceededException ex, RedirectAttributes redirectAttributes)
    {
        User user=null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!( auth instanceof AnonymousAuthenticationToken)){
            user = userService.getUser(auth.getName());
        }
        else{
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "File too big");
        return "redirect:/result?status=error";
    }
}
