package com.woowacourse.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TagTest {
    @DisplayName("constructor: 태그를 생성한다")
    @Test
    void constructor() {
        assertThat(new Tag("taggle")).isInstanceOf(Tag.class);
    }
}
