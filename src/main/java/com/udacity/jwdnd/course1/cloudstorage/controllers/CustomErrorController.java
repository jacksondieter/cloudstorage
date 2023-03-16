package com.udacity.jwdnd.course1.cloudstorage.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        //do something like logging
        log.info("test");
        return "error";
    }

    @Override
    public String getErrorPath() {
        log.info("test1");
        return "/error";
    }
}
