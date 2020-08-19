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
import com.woowacourse.taggle.tag.controller.docs.TagBookmarkDocumentation;
import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.user.domain.User;

public class TagBookmarkControllerTest extends ControllerTest {

    @Autowired
    private TagBookmarkSetup tagBookmarkSetup;

    @Autowired
    private TagSetup tagSetup;

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @Autowired
    private UserSetup userSetup;

    private User user;

    @BeforeEach
    void setUp() {
        user = userSetup.save();
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmarksOfTag: 태그 단건을 조회한다.")
    @Test
    void findBookmarksOfTag() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        readByPathVariables(user, "/api/v1/tags/{tagId}/bookmarks", tag.getId())
                .andExpect(jsonPath("$.id", is(tag.getId().intValue())))
                .andDo(TagBookmarkDocumentation.findBookmarksOfTag());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmarksOfUntagged: Untagged를 조회한다.")
    @Test
    void findBookmarksOfUntagged() throws Exception {
        bookmarkSetup.save(user);

        read(user, "/api/v1/tags/untagged/bookmarks", jsonPath("$.id", is(nullValue())))
                .andDo(TagBookmarkDocumentation.findBookmarksOfUntagged());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("addBookmarkOnTag: 태그에 북마크를 추가한다.")
    @Test
    void addBookmarkOnTag() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);

        createByPathVariables(user, "/api/v1/tags/{tagId}/bookmarks/{bookmarkId}", tag.getId(), bookmark.getId())
                .andDo(TagBookmarkDocumentation.addBookmarkOnTag());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeBookmarkOnTag: 태그에 북마크를 삭제한다.")
    @Test
    void removeBookmarkOnTag() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        removeByPathVariables(user, "/api/v1/tags/{tagId}/bookmarks/{bookmarkId}", tag.getId(), bookmark.getId())
                .andDo(TagBookmarkDocumentation.removeBookmarkOnTag());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findTagsOfBookmark: 북마크의 태그를 조회한다.")
    @Test
    void findTagsOfBookmark() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save(user);
        final Tag tag = tagSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        readByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}/tags", bookmark.getId())
                .andExpect(jsonPath("$.id", is(bookmark.getId().intValue())))
                .andDo(TagBookmarkDocumentation.findTagsOfBookmark());
    }
}