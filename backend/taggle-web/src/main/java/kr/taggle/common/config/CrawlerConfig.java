package kr.taggle.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.taggle.linkpreview.LinkPreviewCrawler;

@Configuration
public class CrawlerConfig {
    private static final String DEFAULT_IMAGE = "https://unsplash.com/a/img/empty-states/photos.png";

    @Bean
    public LinkPreviewCrawler linkPreviewCrawler() {
        return new LinkPreviewCrawler(DEFAULT_IMAGE);
    }
}
