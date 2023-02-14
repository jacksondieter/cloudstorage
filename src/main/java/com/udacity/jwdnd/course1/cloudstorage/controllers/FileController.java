package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileObj;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value="/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody ResponseEntity<Object> dowloadFile(Authentication authentication, @PathVariable Integer fileId){
         FileObj fileObj = fileService.getFile(fileId,authentication.getName());
        byte[] file =fileObj.getFiledata();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file);
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileObj.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        System.out.println("post file");
        String errorMessage = null;
        if (file.isEmpty()){
            errorMessage = "Not File uploaded";
            System.out.println(errorMessage);
        }
        if(file.getSize() > 5 * 1024 * 1024){
            errorMessage = "Large File upload failed";
            System.out.println(errorMessage);
        }
        System.out.println(file.getOriginalFilename() + " ---" + file.getContentType() + " --- " + String.valueOf(file.getSize()));
        int fileAdded = fileService.addFile(file,authentication.getName());
        if(fileAdded < 0){
            errorMessage = "An error occurs. Please try again.";
        }
        if(errorMessage != null){
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/result?status=error";
        }
        return "redirect:/result?status=success";
    }

    @PostMapping("/delete")
    public String deleteFile(Authentication authentication,@ModelAttribute FileObj fileObj){
        System.out.println("delete file");
        System.out.println("noteid: " + fileObj.getFileId());
        fileService.removeFile(fileObj.getFileId(), authentication.getName());
        return "redirect:/result?status=success";
    }

    /*@ExceptionHandler({ MaxUploadSizeExceededException.class })
    public void handleException( MaxUploadSizeExceededException ex) {
        System.out.println("ex");
    }*/
}
