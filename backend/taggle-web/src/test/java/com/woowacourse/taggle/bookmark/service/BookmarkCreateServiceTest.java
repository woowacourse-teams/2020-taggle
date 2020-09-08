package com.woowacourse.taggle.bookmark.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.bookmark.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.bookmark.dto.BookmarkResponse;
import com.woowacourse.taggle.setup.domain.UserSetup;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class BookmarkCreateServiceTest {

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
        assertThat(bookmark.getId()).isEqualTo(1L);
        assertThat(bookmark.getUrl()).isEqualTo("https://github.com");
        assertThat(bookmark.getTitle()).isNotEmpty();
        assertThat(bookmark.getDescription()).isNotEmpty();
        assertThat(bookmark.getImage()).isNotEmpty();
    }
}
