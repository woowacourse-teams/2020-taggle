package com.woowacourse.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookmarkTest {
    private static final String URL = "https://github.com/taggle";

    @DisplayName("constructor: url을 입력받아 북마크를 생성한다")
    @Test
    void constructor() {
        assertThat(new Bookmark(URL)).isInstanceOf(Bookmark.class);
    }

    @DisplayName("addTag: 북마크에 태그를 추가한다")
    @Test
    void addTag() {
        // given
        Bookmark bookmark = new Bookmark(URL);
        Tag tag = new Tag("taggle");

        // when
        bookmark.addTag(tag);

        // then
        assertThat(bookmark.getTags()).hasSize(1);
    }
}
