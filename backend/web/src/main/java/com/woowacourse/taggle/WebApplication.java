package com.woowacourse.taggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-oauth.properties")
public class WebApplication {
    public static void main(final String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
