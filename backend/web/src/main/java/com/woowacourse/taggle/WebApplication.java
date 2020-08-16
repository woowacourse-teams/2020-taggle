package com.woowacourse.taggle;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.woowacourse.taggle.crawler.OpenGraphCrawler;

@SpringBootApplication
public class WebApplication {

    private static final String PROPERTIES = "spring.config.location="
            + "classpath:/config/oauth.properties, "
            + "classpath:/application.yml, "
            + "classpath:/config/application-db.yml";

    public static void main(final String[] args) {
        new SpringApplicationBuilder(WebApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }

    @Bean
    public OpenGraphCrawler openGraphCrawler() {
        return new OpenGraphCrawler();
    }
}
