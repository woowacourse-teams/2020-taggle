package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.BookmarkSetup;
import com.woowacourse.taggle.tag.domain.Bookmark;

class BookmarkControllerTest extends ControllerTest {

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @WithMockUser(value = "ADMIN")
    @DisplayName("createBookmark: 북마크를 추가한다.")
    @Test
    void createBookmark() throws Exception {
        create("/api/v1/bookmarks", "{ \"url\": \"http://github.com\" }");
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() throws Exception {
        bookmarkSetup.save();

        read("/api/v1/bookmarks", jsonPath("$", hasSize(1)));
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeBookmark: 북마크 하나를 제거한다.")
    @Test
    void removeBookmark() throws Exception {
        Bookmark bookmark = bookmarkSetup.save();

        remove("/api/v1/bookmarks/" + bookmark.getId());
    }
}
