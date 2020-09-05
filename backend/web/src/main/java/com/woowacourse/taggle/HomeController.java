package com.woowacourse.taggle;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController implements ErrorController {

    @GetMapping()
    public String index() {
        return "index.html";
    }

    @GetMapping("/error")
    public String redirect() {
        return "index.html";
    }

    @GetMapping("/docs")
    public String docsRedirect() {
        return "/docs/api-guide.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
