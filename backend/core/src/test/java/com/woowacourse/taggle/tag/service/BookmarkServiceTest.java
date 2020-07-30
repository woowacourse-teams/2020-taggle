package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.dto.BookmarkTagResponse;
import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
public class BookmarkServiceTest {

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @DisplayName("북마크를 추가한다.")
    @Test
    void addBookmark() {
        // given
        final BookmarkCreateRequest bookmarkCreateRequest = new BookmarkCreateRequest("https://taggle.co.kr");

        // when
        final BookmarkResponse bookmarkResponse = bookmarkService.createBookmark(bookmarkCreateRequest);

        // then
        assertThat(bookmarkResponse.getUrl()).isEqualTo("https://taggle.co.kr");
    }

    @DisplayName("동일한 북마크가 이미 존재 할 경우 이미 존재하는 북마크를 반환한다.")
    @Test
    void addBookmark_DuplicateBookmark_Exception() {
        // given
        final BookmarkCreateRequest bookmarkCreateRequest = new BookmarkCreateRequest("https://taggle.co.kr");

        // when
        final BookmarkResponse bookmarkResponse = bookmarkService.createBookmark(bookmarkCreateRequest);

        // then
        assertThat(bookmarkResponse.getUrl()).isEqualTo("https://taggle.co.kr");
    }

    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() {
        // given
        final BookmarkCreateRequest bookmarkCreateRequest = new BookmarkCreateRequest("https://taggle.co.kr");
        bookmarkService.createBookmark(bookmarkCreateRequest);

        //when
        final List<BookmarkResponse> bookmarks = bookmarkService.findBookmarks();

        //then
        assertThat(bookmarks).hasSize(1);
    }

    @DisplayName("findBookmark: 하나의 북마크를 조회한다.")
    @Test
    void findBookmark() {
        // given
        final BookmarkCreateRequest bookmarkCreateRequest = new BookmarkCreateRequest("https://taggle.co.kr");
        final BookmarkResponse bookmark = bookmarkService.createBookmark(bookmarkCreateRequest);

        //when
        final BookmarkTagResponse expected = bookmarkService.findBookmark(bookmark.getId());

        // then
        assertThat(expected.getId()).isEqualTo(bookmark.getId());
    }

    @DisplayName("북마크를 삭제한다.")
    @Test
    void removeBookmark() {
        // given
        final Bookmark bookmark = bookmarkRepository.save(new Bookmark("https://taggle.co.kr"));
        bookmarkRepository.save(new Bookmark("https://naver.co.kr"));

        // when
        bookmarkService.removeBookmark(bookmark.getId());
        // then
        assertThat(bookmarkRepository.findAll()).hasSize(1);
    }

    @DisplayName("삭제하려는 북마크가 존재 하지 않을 경우 익셉션을 발생한다")
    @Test
    void removeBookmark_NotFoundException() {
        // given
        final Bookmark bookmark = bookmarkRepository.save(new Bookmark("https://taggle.co.kr"));

        // when
        // then
        assertThatThrownBy(() -> bookmarkService.removeBookmark(bookmark.getId() + 1L))
                .isInstanceOf(BookmarkNotFoundException.class)
                .hasMessageContaining("삭제하려는 북마크가 존재하지 않습니다");
    }
}
