package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.BookmarkSetup;
import com.woowacourse.taggle.setup.domain.CategorySetup;
import com.woowacourse.taggle.setup.domain.TagBookmarkSetup;
import com.woowacourse.taggle.setup.domain.TagSetup;
import com.woowacourse.taggle.tag.controller.docs.BookmarkDocumentation;
import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.Tag;

class BookmarkControllerTest extends ControllerTest {

    @Autowired
    private TagBookmarkSetup tagBookmarkSetup;

    @Autowired
    private CategorySetup categorySetup;

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @Autowired
    private TagSetup tagSetup;

    @WithMockUser(value = "ADMIN")
    @DisplayName("createBookmark: 북마크를 추가한다.")
    @Test
    void createBookmark() throws Exception {
        Category category = categorySetup.saveWithUser();
        createByJsonParams("/api/v1/bookmarks", "{ \"url\": \"http://github.com\" }", category)
                .andDo(BookmarkDocumentation.createBookmark());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmark: 하나의 북마크를 조회한다.")
    @Test
    void findBookmark() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save();
        final Tag tag = tagSetup.save();
        tagBookmarkSetup.save(tag, bookmark);

        readByBookmarkPathVariables("/api/v1/bookmarks/" + bookmark.getId() + "/tags", bookmark)
                .andExpect(jsonPath("$.id", is(bookmark.getId().intValue())))
                .andDo(BookmarkDocumentation.findBookmark());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() throws Exception {
        readBookmark("/api/v1/bookmarks", jsonPath("$", hasSize(1)), bookmarkSetup.save())
                .andDo(BookmarkDocumentation.findBookmarks());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeBookmark: 북마크 하나를 제거한다.")
    @Test
    void removeBookmark() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save();

        removeBookmark("/api/v1/bookmarks/" + bookmark.getId(), bookmark)
                .andDo(BookmarkDocumentation.removeBookmark());
    }
}
