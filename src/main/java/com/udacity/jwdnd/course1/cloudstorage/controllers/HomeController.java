package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model){
        System.out.println("get home");
        List<Note> noteList = noteService.getNotes(authentication.getName());
        System.out.println(noteList);
        model.addAttribute("noteList",noteList);
        return "home";
    }

}
