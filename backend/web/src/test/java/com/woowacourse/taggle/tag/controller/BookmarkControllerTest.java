package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.BookmarkSetup;
import com.woowacourse.taggle.setup.domain.TagBookmarkSetup;
import com.woowacourse.taggle.setup.domain.TagSetup;
import com.woowacourse.taggle.setup.domain.UserSetup;
import com.woowacourse.taggle.tag.controller.docs.BookmarkDocumentation;
import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.user.domain.User;

class BookmarkControllerTest extends ControllerTest {

    @Autowired
    private TagBookmarkSetup tagBookmarkSetup;

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @Autowired
    private TagSetup tagSetup;

    @Autowired
    private UserSetup userSetup;

    private User user;

    @BeforeEach
    void setUp() {
        user = userSetup.save();
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("createBookmark: 북마크를 추가한다.")
    @Test
    void createBookmark() throws Exception {
        createByJsonParams(user, "/api/v1/bookmarks", "{ \"url\": \"http://github.com\" }")
                .andDo(BookmarkDocumentation.createBookmark());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmark: 하나의 북마크를 조회한다.")
    @Test
    void findBookmark() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save(user);
        final Tag tag = tagSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        readByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}/tags", bookmark.getId())
                .andExpect(jsonPath("$.id", is(bookmark.getId().intValue())))
                .andDo(BookmarkDocumentation.findBookmark());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() throws Exception {
        bookmarkSetup.save(user);
        readBookmark(user, "/api/v1/bookmarks", jsonPath("$", hasSize(1)))
                .andDo(BookmarkDocumentation.findBookmarks());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeBookmark: 북마크 하나를 제거한다.")
    @Test
    void removeBookmark() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save(user);

        removeByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}", bookmark.getId())
                .andDo(BookmarkDocumentation.removeBookmark());
    }
}
