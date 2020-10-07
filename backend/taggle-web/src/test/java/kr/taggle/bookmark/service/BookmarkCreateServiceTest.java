package kr.taggle.bookmark.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.taggle.ServiceTest;
import kr.taggle.bookmark.dto.BookmarkCreateRequest;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.setup.domain.UserSetup;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;

class BookmarkCreateServiceTest extends ServiceTest {

    @Autowired
    private BookmarkCreateService bookmarkCreateService;

    @Autowired
    private UserSetup userSetup;

    private SessionUser user;

    @BeforeEach
    void setUp() {
        final User testUser = userSetup.save();
        user = new SessionUser(testUser);
    }

    @DisplayName("createBookmark: 북마크를 추가한다.")
    @Test
    void createBookmark() {
        // given
        final BookmarkCreateRequest bookmarkCreateDto = new BookmarkCreateRequest("https://github.com");

        // when
        final BookmarkResponse bookmark = bookmarkCreateService.createBookmark(bookmarkCreateDto, user);

        // then
        assertAll(() -> {
            assertThat(bookmark.getId()).isNotNull();
            assertThat(bookmark.getUrl()).isEqualTo("https://github.com");
            assertThat(bookmark.getTitle()).isNotEmpty();
            assertThat(bookmark.getDescription()).isNotEmpty();
            assertThat(bookmark.getImage()).isNotEmpty();
        });
    }
}
