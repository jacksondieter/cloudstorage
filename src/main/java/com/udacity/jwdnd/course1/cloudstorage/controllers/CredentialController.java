package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/credential")
public class CredentialController {
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }


    @PostMapping
    public String createCredential(Authentication authentication, @ModelAttribute Credential credential, RedirectAttributes redirectAttributes) {
        log.info("credential createCredential");
        String errorMessage = null;
        if (credential.getUsername().length() > 20 || credential.getPassword().length() > 20) {
            errorMessage = "The username is too long.";
        } else {
            if (credential.getCredentialid() != null) {
                boolean credentialUpdate = credentialService.updateCredential(credential, authentication.getName());
                if (!credentialUpdate) {
                    errorMessage = "An error occurs. Please try again.";
                }
            } else {
                int credentialCreated = credentialService.createCredential(credential, authentication.getName());
                if (credentialCreated < 0) {
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
    public String deleteCredential(Authentication authentication, @ModelAttribute Credential credential) {
        log.info("credential deleteCredential");
        log.debug("credential id: " + credential.getCredentialid());
        credentialService.removeCredential(credential, authentication.getName());
        return "redirect:/result?status=success";
    }

}
