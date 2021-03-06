package kr.taggle.bookmark.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.domain.BookmarkRepository;
import kr.taggle.bookmark.dto.BookmarkCreateDto;
import kr.taggle.bookmark.dto.BookmarkPageRequest;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.bookmark.exception.BookmarkNotFoundException;
import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;

@Transactional
@SpringBootTest
class BookmarkServiceTest {
    private static final BookmarkPageRequest BOOKMARK_FIND_REQUEST = new BookmarkPageRequest(1, 10);

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UserService userService;

    private SessionUser user;

    @BeforeEach
    void setUp() {
        final User testUser = userService.save(User.builder()
                .email("jordyLover@kakao.com")
                .notificationEmail("jordyLover@kakao.com")
                .nickName("tigger")
                .role(Role.USER)
                .picture("https://www.naver.com/")
                .notificationEnabled(false)
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

    @DisplayName("createBookmark: 이미 같은 이름의 url을 가진 북마크가 존재 하는 경우, 기존의 북마크를 반환한다.")
    @Test
    void createBookmark_DuplicateBookmark() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");

        // when
        final BookmarkResponse bookmarkResponse = bookmarkService.createBookmark(user, bookmarkCreateRequest);
        final BookmarkResponse bookmarkResponseWithSameName = bookmarkService.createBookmark(user,
                bookmarkCreateRequest);

        // then
        assertThat(bookmarkResponse.getId()).isEqualTo(bookmarkResponseWithSameName.getId());
        assertThat(bookmarkResponse.getUrl()).isEqualTo(bookmarkResponseWithSameName.getUrl());
        assertThat(bookmarkResponse.getTitle()).isEqualTo(bookmarkResponseWithSameName.getTitle());
        assertThat(bookmarkResponse.getDescription()).isEqualTo(bookmarkResponseWithSameName.getDescription());
        assertThat(bookmarkResponse.getImage()).isEqualTo(bookmarkResponseWithSameName.getImage());
    }

    @DisplayName("findBookmarks: 북마크를 1 페이지를 조회한다.")
    @Test
    void findBookmarks() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");
        bookmarkService.createBookmark(user, bookmarkCreateRequest);

        //when
        final List<BookmarkResponse> bookmarks = bookmarkService.findBookmarks(user, BOOKMARK_FIND_REQUEST);

        //then
        assertThat(bookmarks).hasSize(1);
    }

    @DisplayName("removeBookmark: 북마크를 삭제한다.")
    @Test
    void removeBookmark() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto("https://taggle.co.kr", "title",
                "description", "image");
        final BookmarkResponse bookmarkResponse = bookmarkService.createBookmark(user, bookmarkCreateRequest);

        // when
        bookmarkService.removeBookmark(user, bookmarkResponse.getId());
        final List<BookmarkResponse> bookmarkResponses = bookmarkService.findBookmarks(user, BOOKMARK_FIND_REQUEST);

        // then
        assertThat(bookmarkResponses).isEmpty();
    }

    @DisplayName("removeBookmark: 삭제하려는 북마크가 존재 하지 않을 경우 익셉션을 발생한다")
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
