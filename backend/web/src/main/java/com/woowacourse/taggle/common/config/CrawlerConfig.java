package com.woowacourse.taggle.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woowacourse.taggle.linkpreview.LinkPreviewCrawler;

@Configuration
public class CrawlerConfig {
    private final static String DEFAULT_IMAGE = "https://unsplash.com/a/img/empty-states/photos.png";

    @Bean
    public LinkPreviewCrawler linkPreviewCrawler() {
        return new LinkPreviewCrawler(DEFAULT_IMAGE);
    }
}
