package com.woowacourse.taggle.common.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController implements ErrorController {

    @GetMapping
    public String index() {
        return "index.html";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/error")
    public String redirect() {
        return "index.html";
    }

    @GetMapping("/docs")
    public String docs() {
        return "/docs/api-guide.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
