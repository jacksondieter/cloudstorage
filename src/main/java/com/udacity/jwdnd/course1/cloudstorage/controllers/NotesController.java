package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/note")
public class NotesController {
    private final NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping
    public String createNote(Authentication authentication, @ModelAttribute Note note, RedirectAttributes redirectAttributes) {
        log.info("note createNote");
        String errorMessage = null;
        Note existingNote = noteService.getNoteByTitle(authentication.getName(), note.getNotetitle());
        if (existingNote != null) {
            errorMessage = "The title already exists.";
        } else if (note.getNotedescription().length() > 200) {
            errorMessage = "The description is too long.";
        } else {
            if (note.getNoteid() != null) {
                boolean noteUpdated = noteService.updateNote(note, authentication.getName());
                if (!noteUpdated) {
                    errorMessage = "An error occurs. Please try again.";
                }
            } else {
                int noteCreated = noteService.createNote(note, authentication.getName());
                if (noteCreated < 0) {
                    errorMessage = "An error occurs. Please try again.";
                }
            }
        }
        if (errorMessage == null) {
            return "redirect:/result?status=success";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/result?status=error";
        }
    }

    @PostMapping("/delete")
    public String deleteNote(Authentication authentication, @ModelAttribute Note note) {
        log.info("note deleteNote");
        log.debug("noteid: " + note.getNoteid());
        noteService.removeNote(note, authentication.getName());
        return "redirect:/result?status=success";
    }

}
