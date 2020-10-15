package kr.taggle.bookmark.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.dto.BookmarkCreateRequest;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;

@Transactional
@SpringBootTest
class BookmarkCreateServiceTest {

    @Autowired
    private BookmarkCreateService bookmarkCreateService;

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
