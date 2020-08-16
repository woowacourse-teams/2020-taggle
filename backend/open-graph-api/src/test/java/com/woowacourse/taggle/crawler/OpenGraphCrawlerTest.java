package com.woowacourse.taggle.crawler;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.dto.OpenGraphDto;
import com.woowacourse.taggle.exception.InvalidURLException;

class OpenGraphCrawlerTest {

    private final OpenGraphCrawler openGraphCrawler;

    public OpenGraphCrawlerTest() {
        this.openGraphCrawler = new OpenGraphCrawler();
    }

    @DisplayName("extractPreview: 오픈 그래프를 제대로 크롤링 하는지 확인하는 테스트")
    @Test
    void extractPreviewTest() {
        OpenGraphDto openGraphDto = openGraphCrawler.findOpenGraph("https://github.com");
        assertThat(openGraphDto.getTitle()).isEqualTo("Build software better, together");
        assertThat(openGraphDto.getDescription())
                .isEqualTo(
                        "GitHub is where people build software. More than 50 million people use GitHub to discover, fork, and contribute to over 100 million projects.");
        assertThat(openGraphDto.getImage())
                .isEqualTo("https://github.githubassets.com/images/modules/open_graph/github-logo.png");
    }

    @DisplayName("extractPreview: 오픈그래프에서 타이틀이 없다면 title 태그의 값을 가져오는 테스트")
    @Test
    void extractPreviewTestReturnTitleTag() {
        OpenGraphDto openGraphDto = openGraphCrawler.findOpenGraph(
                "http://info.cern.ch/");
        assertThat(openGraphDto.getTitle()).isEqualTo("http://info.cern.ch");
    }

    @DisplayName("extractPreview: 잘못된 url이 들어왔을 경우 예외처")
    @Test
    void extractPreviewTestExceptionIncorrectURL() {
        assertThatThrownBy(() -> openGraphCrawler.findOpenGraph("taggle"))
                .isInstanceOf(InvalidURLException.class)
                .hasMessageContaining("잘못된 URL입니다.");
    }
}
