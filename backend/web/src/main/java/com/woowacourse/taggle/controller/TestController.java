package com.woowacourse.taggle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {

    @GetMapping
    public String logTest() {
        for (int i = 0; i < 51; i++) {
            log.info("Rolling file appender example...");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i%2==0) {
                log.error("error");
            }
        }
        return "html";
    }
}
