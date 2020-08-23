package com.woowacourse.taggle.linkpreview;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.exception.CrawlerConnectionException;
import com.woowacourse.taggle.exception.InvalidURLException;

class LinkPreviewCrawlerTest {

    private final LinkPreviewCrawler linkPreviewCrawler;

    public LinkPreviewCrawlerTest() {
        this.linkPreviewCrawler = new LinkPreviewCrawler(
                "https://lh3.googleusercontent.com/proxy/fFK04pFoxl4RBXgnvtpNYZgSIOST8suUpzhcyoXtMSi3XxrUeTnNKpqZeONXJD0hiuZWNpO1xz2_T5A0e6fG0Q-xkSVlZhiZK5fdXxAuOjGk8HFLPQ");
    }

    @DisplayName("extractPreview: 오픈 그래프를 제대로 크롤링 하는지 확인하는 테스트")
    @Test
    void extractPreviewTest() {
        final LinkPreview linkPreview = linkPreviewCrawler.extractPreview("https://github.com");
        assertThat(linkPreview.getTitle()).isEqualTo("Build software better, together");
        assertThat(linkPreview.getDescription())
                .isEqualTo(
                        "GitHub is where people build software. More than 50 million people use GitHub to discover, fork, and contribute to over 100 million projects.");
        assertThat(linkPreview.getImage())
                .isEqualTo("https://github.githubassets.com/images/modules/open_graph/github-logo.png");
    }

    @DisplayName("extractPreview: 오픈그래프에서 타이틀이 없다면 title 태그의 값을 가져오는 테스트")
    @Test
    void extractPreviewTestReturnTitleTag() {
        final LinkPreview linkPreview = linkPreviewCrawler.extractPreview("http://info.cern.ch/");
        assertThat(linkPreview.getTitle()).isEqualTo("http://info.cern.ch");
    }

    @DisplayName("extractPreview: 잘못된 url이 들어왔을 경우 예외처")
    @Test
    void extractPreviewTestExceptionIncorrectURL() {
        assertThatThrownBy(() -> linkPreviewCrawler.extractPreview("taggle"))
                .isInstanceOf(InvalidURLException.class)
                .hasMessageContaining("잘못된 URL입니다.");
    }

    @DisplayName("extractPreview: 존재하지 않은 url을 입력했을 경우 예외처리")
    @Test
    void extractPreviewExceptionCrawlerConnection() {
        assertThatThrownBy(() -> linkPreviewCrawler.extractPreview("https://nav11.co"))
                .isInstanceOf(CrawlerConnectionException.class)
                .hasMessageContaining("연결이 되지 않았습니다.");

    }

    @DisplayName("extractPreview: 리다이렉트 url이 들어왔을 경우 리다이렉트 url을 크롤링 하기")
    @Test
    void extractPreviewReturnRedirectUrl() {
        final LinkPreview linkPreview = linkPreviewCrawler.extractPreview("https://youtu.be/F_vBAbjj4Pk");

        assertThat(linkPreview.getTitle()).contains("티거의 Web server vs WAS");
        assertThat(linkPreview.getDescription()).contains("우아한테크코스의 크루들이 진행하는 10분 테크토크입니다.");
        assertThat(linkPreview.getImage()).isEqualTo("https://i.ytimg.com/vi/F_vBAbjj4Pk/maxresdefault.jpg");
    }
}
