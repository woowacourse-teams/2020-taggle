package com.woowacourse.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.taggle.bookmark.exception.UrlFormatMismatchException;

class LinkTest {
    @DisplayName("constructor: url을 인자로 받아 인스턴스 생성")
    @ParameterizedTest
    @ValueSource(strings = {"https://github.com", "naver.com", "bit.ly/hello"})
    void allArgsConstructor(final String url) {
        assertThat(new Link(url))
                .isInstanceOf(Link.class);
    }

    @DisplayName("constructor: url의 형태가 올바르지 않을 때 예외 발생")
    @Test
    void allArgsConstructor_ExceptionThrown() {
        assertThatThrownBy(() -> new Link("naver. com"))
                .isInstanceOf(UrlFormatMismatchException.class)
                .hasMessageContaining("url이 올바르지 않습니다");
    }
}
