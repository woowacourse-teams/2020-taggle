package com.woowacourse.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.bookmark.exception.AlreadyExistBookmarkException;

public class BookmarksTest {
    @DisplayName("empty: 빈 bookmarks 인스턴스 생성")
    @Test
    void empty() {
        Bookmarks bookmarks = Bookmarks.empty();
        assertAll(
                () -> assertThat(bookmarks).isInstanceOf(Bookmarks.class),
                () -> assertThat(bookmarks.getBookmarks()).hasSize(0)
        );
    }

    @DisplayName("addBookmark: 새로운 북마크 추가")
    @Test
    void addBookmark() {
        // given
        Link link = new Link("https://github.com/taggle");
        Bookmark bookmark = new Bookmark(link);
        Bookmarks bookmarks = Bookmarks.empty();

        // when
        bookmarks.addBookmark(bookmark);

        // then
        assertThat(bookmarks.getBookmarks()).hasSize(1);
    }

    @DisplayName("addBookmark: 이미 존재하는 북마크를 추가하려고 할 때 예외 발생")
    @Test
    void addBookmark_ExistBookmark_ExceptionThrown() {
        // given
        Link link = new Link("https://github.com/taggle");
        Bookmark bookmark = new Bookmark(link);
        Bookmark duplicateBookmark = new Bookmark(link);
        Bookmarks bookmarks = Bookmarks.empty();
        bookmarks.addBookmark(bookmark);

        // when
        // then
        assertThatThrownBy(() -> bookmarks.addBookmark(duplicateBookmark))
                .isInstanceOf(AlreadyExistBookmarkException.class)
                .hasMessageContaining("이미 존재하는 북마크입니다");
    }

    @DisplayName("addBookmark: 새로운 북마크 추가")
    @Test
    void removeBookmark() {
        // given
        Link link = new Link("https://github.com/taggle");
        Bookmark bookmark = new Bookmark(link);
        Bookmarks bookmarks = Bookmarks.empty();
        bookmarks.addBookmark(bookmark);

        // when
        bookmarks.removeBookmark(bookmark);

        // then
        assertThat(bookmarks.getBookmarks()).hasSize(0);
    }
}
