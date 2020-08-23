package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.setup.domain.UserSetup;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
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
        final BookmarkCreateRequest bookmarkCreateDto = new BookmarkCreateRequest("http://naver.com");

        // when
        final BookmarkResponse bookmark = bookmarkCreateService.createBookmark(bookmarkCreateDto, user);

        // then
        assertThat(bookmark.getId()).isEqualTo(1L);
        assertThat(bookmark.getUrl()).isEqualTo("http://naver.com");
        assertThat(bookmark.getTitle()).isEqualTo("네이버");
        assertThat(bookmark.getDescription()).isEqualTo("네이버");
        assertThat(bookmark.getImage()).isEqualTo(
                "https://s.pstatic.net/static/www/mobile/edit/2016/0705/mobile_212852414260.png");
    }
}
