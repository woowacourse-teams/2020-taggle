package com.woowacourse.taggle;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebApplication {

    private static final String PROPERTIES = "spring.config.location="
            + "classpath:/config/oauth.properties, "
            + "classpath:/application.properties";

    public static void main(final String[] args) {
        new SpringApplicationBuilder(WebApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }
}
