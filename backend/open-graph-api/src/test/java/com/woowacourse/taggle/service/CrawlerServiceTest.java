package com.woowacourse.taggle.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.dto.OpenGraphDto;

class CrawlerServiceTest {

    private final CrawlerService crawlerService;

    public CrawlerServiceTest() {
        this.crawlerService = new CrawlerService();
    }

    @DisplayName("openGraphCrawler: 오픈 그래프를 제대로 크롤링 하는지 확인하는 테스트")
    @Test
    void openGraphCrawlerTest() {
        OpenGraphDto openGraphDto = crawlerService.openGraphCrawler("https://github.com");
        assertThat(openGraphDto.getTitle()).isEqualTo("Build software better, together");
        assertThat(openGraphDto.getDescription())
                .isEqualTo(
                        "GitHub is where people build software. More than 50 million people use GitHub to discover, fork, and contribute to over 100 million projects.");
        assertThat(openGraphDto.getImage())
                .isEqualTo("https://github.githubassets.com/images/modules/open_graph/github-logo.png");
    }
}
