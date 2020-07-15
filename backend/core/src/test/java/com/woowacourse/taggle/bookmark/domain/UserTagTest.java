package com.woowacourse.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.bookmark.exception.AlreadyExistBookmarkException;
import com.woowacourse.taggle.usertag.UserTag;

public class UserTagTest {
    @DisplayName("constructor: 유저 태그 생성")
    @Test
    void constructor() {
        Tag tag = new Tag("taggle");
        assertThat(new UserTag(tag)).isInstanceOf(UserTag.class);
    }

    @DisplayName("addBookmark: 사용자 태그에 북마크 등록")
    @Test
    void addBookmark() {
        // given
        Tag tag = new Tag("taggle");
        UserTag userTag = new UserTag(tag);
        Link link = new Link("https://helloworld.com");
        Bookmark addedBookmark = new Bookmark(link);

        // when
        userTag.addBookmark(addedBookmark);

        // then
        List<Bookmark> bookmarks = userTag.getBookmarks().getBookmarks();
        assertThat(bookmarks).anyMatch(bookmark -> bookmark.getLink().equals(link));
        assertThat(bookmarks).anyMatch(bookmark -> bookmark.getTags().getTags().contains(tag));
    }

    @DisplayName("addBookmark: 사용자 태그에 동일한 북마크를 등록 시도할 때 예외 발생")
    @Test
    void addBookmark_DuplicateBookmark_ExceptionThrown() {
        // given
        Tag tag = new Tag("taggle");
        UserTag userTag = new UserTag(tag);
        Link link = new Link("https://helloworld.com");
        Bookmark addedBookmark = new Bookmark(link);
        Bookmark duplicatedBookmark = new Bookmark(link);
        userTag.addBookmark(addedBookmark);

        // when
        // then
        assertThatThrownBy(() -> userTag.addBookmark(duplicatedBookmark))
                .isInstanceOf(AlreadyExistBookmarkException.class)
                .hasMessageContaining("이미 존재하는 북마크입니다");
    }

    @DisplayName("removeBookmark: 사용자 태그에 등록된 북마크 제거")
    @Test
    void removeBookmark() {
        // given
        Tag tag = new Tag("taggle");
        UserTag userTag = new UserTag(tag);
        Link link = new Link("https://helloworld.com");
        Bookmark addedBookmark = new Bookmark(link);
        userTag.addBookmark(addedBookmark);

        // when
        userTag.removeBookmark(addedBookmark);

        // then
        List<Bookmark> bookmarks = userTag.getBookmarks().getBookmarks();
        assertThat(bookmarks).noneMatch(bookmark -> bookmark.getLink().equals(link));
    }
}
