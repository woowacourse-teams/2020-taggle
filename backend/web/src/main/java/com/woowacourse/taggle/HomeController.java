package com.woowacourse.taggle;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController implements ErrorController {

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String redirect() {
        return "index";
    }

    @GetMapping("/docs")
    public String docsIndex() {
        return "../static/docs/api-guide.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
