package com.woowacourse.taggle.bookmark.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.bookmark.docs.BookmarkDocumentation;
import com.woowacourse.taggle.bookmark.domain.Bookmark;
import com.woowacourse.taggle.setup.domain.BookmarkSetup;
import com.woowacourse.taggle.setup.domain.UserSetup;
import com.woowacourse.taggle.user.domain.User;

class BookmarkControllerTest extends ControllerTest {

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @Autowired
    private UserSetup userSetup;

    private User user;

    @BeforeEach
    void setUp() {
        user = userSetup.save();
    }

    @DisplayName("createBookmark: 북마크를 추가한다.")
    @Test
    void createBookmark() throws Exception {
        createByJsonParams(user, "/api/v1/bookmarks", "{ \"url\": \"http://github.com\" }")
                .andDo(BookmarkDocumentation.createBookmark());
    }

    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() throws Exception {
        bookmarkSetup.save(user);
        read(user, "/api/v1/bookmarks?offset=1&limit=10", jsonPath("$", hasSize(1)))
                .andDo(BookmarkDocumentation.findBookmarks());
    }

    @DisplayName("removeBookmark: 북마크 하나를 제거한다.")
    @Test
    void removeBookmark() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save(user);

        removeByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}", bookmark.getId())
                .andDo(BookmarkDocumentation.removeBookmark());
    }
}
