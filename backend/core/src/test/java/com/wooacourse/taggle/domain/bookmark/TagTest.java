package com.wooacourse.taggle.domain.bookmark;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.wooacourse.taggle.domain.bookmark.exception.EmptyValueException;

class TagTest {
    @DisplayName("constructor: 빈 생성자로 Tag 인스턴스 생성")
    @Test
    void noArgsConstructor() {
        assertThat(new Tag())
                .isInstanceOf(Tag.class);
    }

    @DisplayName("constructor: 인자가 있는 경우 Tag 인스턴스 생성")
    @Test
    void allArgsConstructor() {
        assertThat(new Tag("java"))
                .isInstanceOf(Tag.class);
    }

    @DisplayName("constructor: 이름이 빈 문자열로 입력된 경우 예외 발생")
    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"        "})
    void allArgsConstructor_ExceptionThrown(final String name) {
        assertThatThrownBy(() -> new Tag(name))
                .isInstanceOf(EmptyValueException.class)
                .hasMessageContaining("name이 존재하지 않습니다");
    }
}
