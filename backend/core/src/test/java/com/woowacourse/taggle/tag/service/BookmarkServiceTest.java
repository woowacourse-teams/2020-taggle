package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.dto.BookmarkCreateDto;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.dto.BookmarkTagResponse;
import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;
import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class BookmarkServiceTest {

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    private UserService userService;

    private SessionUser user;

    @BeforeEach
    void setUp() {
        final User testUser = userService.save(User.builder()
                .email("a@a.com")
                .nickName("tigger")
                .role(Role.USER)
                .picture("https://www.naver.com/")
                .build());
        user = new SessionUser(testUser);
    }

    @DisplayName("createBookmark: 북마크를 추가한다.")
    @Test
    void createBookmark() {
        // given
        final BookmarkCreateDto bookmarkCreateDto = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");

        // when
        final BookmarkResponse bookmarkResponse = bookmarkService.createBookmark(user, bookmarkCreateDto);

        // then
        assertThat(bookmarkResponse.getUrl()).isEqualTo("https://taggle.co.kr");
    }

    @DisplayName("동일한 북마크가 이미 존재 할 경우 이미 존재하는 북마크를 반환한다.")
    @Test
    void createBookmark_DuplicateBookmark_ExceptionThrown() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");

        // when
        final BookmarkResponse bookmarkResponse = bookmarkService.createBookmark(user, bookmarkCreateRequest);

        // then
        assertThat(bookmarkResponse.getUrl()).isEqualTo("https://taggle.co.kr");
    }

    @DisplayName("findBookmark: 하나의 북마크를 조회한다.")
    @Test
    void findBookmark() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");
        final BookmarkResponse bookmark = bookmarkService.createBookmark(user, bookmarkCreateRequest);

        //when
        final BookmarkTagResponse expected = bookmarkService.findBookmark(user, bookmark.getId());

        // then
        assertThat(expected.getId()).isEqualTo(bookmark.getId());
    }

    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");
        bookmarkService.createBookmark(user, bookmarkCreateRequest);

        //when
        final List<BookmarkResponse> bookmarks = bookmarkService.findBookmarks(user);

        //then
        assertThat(bookmarks).hasSize(1);
    }

    @DisplayName("북마크를 삭제한다.")
    @Test
    void removeBookmark() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");
        final BookmarkResponse bookmarkResponse = bookmarkService.createBookmark(user, bookmarkCreateRequest);

        // when
        bookmarkService.removeBookmark(user, bookmarkResponse.getId());
        final List<BookmarkResponse> bookmarkResponses = bookmarkService.findBookmarks(user);
        // then
        assertThat(bookmarkResponses).hasSize(0);
    }

    @DisplayName("삭제하려는 북마크가 존재 하지 않을 경우 익셉션을 발생한다")
    @Test
    void removeBookmark_NotFoundException() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");
        final BookmarkResponse bookmarkResponse = bookmarkService.createBookmark(user, bookmarkCreateRequest);

        // when
        // then
        assertThatThrownBy(() -> bookmarkService.removeBookmark(user, 0L))
                .isInstanceOf(BookmarkNotFoundException.class)
                .hasMessageContaining("북마크가 존재하지 않습니다");
    }
}
