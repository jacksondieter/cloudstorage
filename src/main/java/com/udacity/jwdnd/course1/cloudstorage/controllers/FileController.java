package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileObj;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody ResponseEntity<Object> downloadFile(Authentication authentication, @PathVariable Integer fileId) {
        log.info("file downloadFile");
        FileObj fileObj = fileService.getFileById(fileId, authentication.getName());
        byte[] file = fileObj.getFiledata();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileObj.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException, MaxUploadSizeExceededException {
        log.info("file uploadFile");
        String errorMessage = null;
        FileObj exitingFile = fileService.getFileByFilename(file.getOriginalFilename(), authentication.getName());
        if (exitingFile != null) {
            errorMessage = "File already uploaded";
            log.error(errorMessage);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/result?status=error";
        }
        log.debug(file.getOriginalFilename() + " ---" + file.getContentType() + " --- " + String.valueOf(file.getSize()));
        int fileAdded = fileService.addFile(file, authentication.getName());
        if (fileAdded < 0) {
            errorMessage = "An error occurs. Please try again.";
            log.error(errorMessage);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/result?status=error";
        }
        return "redirect:/result?status=success";
    }

    @PostMapping("/delete")
    public String deleteFile(Authentication authentication, @ModelAttribute FileObj fileObj) {
        log.info("file deleteFile");
        log.debug("noteid: " + fileObj.getFileId());
        fileService.removeFile(fileObj.getFileId(), authentication.getName());
        return "redirect:/result?status=success";
    }
}
