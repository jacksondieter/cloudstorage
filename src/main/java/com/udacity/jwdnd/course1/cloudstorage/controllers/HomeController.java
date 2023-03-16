package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FileService fileService;

    public HomeController(NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {
        log.info("home getHomePage");
        log.debug(authentication.toString());
        List<Note> noteList = noteService.getNotes(authentication.getName());
        model.addAttribute("noteList", noteList);
        List<Credential> credentialList = credentialService.getCredentials(authentication.getName());
        log.debug(credentialList.toString());
        model.addAttribute("credentialList", credentialList);
        List<Map<String, Object>> fileList = fileService.getFilenames(authentication.getName());
        log.debug(fileList.toString());
        model.addAttribute("fileList", fileList);
        return "home";
    }

}
