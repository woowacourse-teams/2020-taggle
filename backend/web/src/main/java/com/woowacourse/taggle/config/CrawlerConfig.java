package com.woowacourse.taggle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woowacourse.taggle.crawler.OpenGraphCrawler;

@Configuration
public class CrawlerConfig {
    private final static String DEFAULT_IMAGE = "https://lh3.googleusercontent.com/proxy/fFK04pFoxl4RBXgnvtpNYZgSIOST8suUpzhcyoXtMSi3XxrUeTnNKpqZeONXJD0hiuZWNpO1xz2_T5A0e6fG0Q-xkSVlZhiZK5fdXxAuOjGk8HFLPQ";

    @Bean
    public OpenGraphCrawler openGraphCrawler() {
        return new OpenGraphCrawler(DEFAULT_IMAGE);
    }
}
