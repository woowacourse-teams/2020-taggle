package com.woowacourse.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.bookmark.exception.AlreadyExistTagException;

public class BookmarkTest {
    private static final Link LINK = new Link("https://github.com/taggle");

    @DisplayName("constructor: 북마크 생성")
    @Test
    void constructor() {
        assertThat(new Bookmark(LINK)).isInstanceOf(Bookmark.class);
    }

    @DisplayName("addTag: 북마크에 태그 동록")
    @Test
    void addTag() {
        // given
        Bookmark bookmark = new Bookmark(LINK);
        Tag tag = new Tag("taggle");

        // when
        bookmark.addTag(tag);

        // then
        assertThat(bookmark.getTags().getTags()).hasSize(1);
    }

    @DisplayName("addTag: 북마크에 중복태그를 추가시 예외 발생")
    @Test
    void addTag_DuplicateTag_ExceptionThrown() {
        // given
        Bookmark bookmark = new Bookmark(LINK);
        Tag tag = new Tag("taggle");
        Tag duplicateTag = new Tag("taggle");
        bookmark.addTag(tag);

        // when
        // then
        assertThatThrownBy(() -> bookmark.addTag(duplicateTag))
                .isInstanceOf(AlreadyExistTagException.class)
                .hasMessageContaining("이미 존재하는 태그입니다");
    }
}
