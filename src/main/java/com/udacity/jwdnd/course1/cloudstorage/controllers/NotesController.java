package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NotesController {
    private final NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping
    public String createNote(Authentication authentication, @ModelAttribute Note note, RedirectAttributes redirectAttributes){
        System.out.println("post note");
        String errorMessage = null;
        if (note.getNotedescription().length() > 200) {
            errorMessage = "The description is too long.";
        } else {
            if (note.getNoteid() != null){
               boolean noteUpdated = noteService.updateNote(note,authentication.getName());
                if(!noteUpdated){
                    errorMessage = "An error occurs. Please try again.";
                }
            } else {
                int noteCreated = noteService.createNote(note,authentication.getName());
                if(noteCreated < 0){
                    errorMessage = "An error occurs. Please try again.";
                }
            }
        }
        if(errorMessage == null){
            return "redirect:/result?status=success";
        }else {
            redirectAttributes.addFlashAttribute("errorMessage",errorMessage);
            return "redirect:/result?status=error";
        }
    }

    @PostMapping("/delete")
    public String deleteNote(Authentication authentication,@ModelAttribute Note note){
        System.out.println("delete note");
        System.out.println("noteid: " + note.getNoteid());
        noteService.removeNote(note,authentication.getName());
        return "redirect:/result?status=success";
    }

}
