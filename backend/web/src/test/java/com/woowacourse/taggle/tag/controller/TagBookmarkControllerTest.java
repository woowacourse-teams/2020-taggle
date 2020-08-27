package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @DisplayName("findBookmarksByTagId: 단건 태그와 해당 태그에 속해있는 북마크 목록을 조회한다.")
    @Test
    void findBookmarksByTagId() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        readByPathVariables(user, "/api/v1/tags/{tagId}/bookmarks?limit=10&offset=1", tag.getId())
                .andExpect(jsonPath("$.id", is(tag.getId().intValue())))
                .andDo(TagBookmarkDocumentation.findBookmarksByTagId());
    }

    @DisplayName("findUntaggedBookmarks: 태그가 없는 북마크 목록을 조회한다.")
    @Test
    void findUntaggedBookmarks() throws Exception {
        bookmarkSetup.save(user);

        read(user, "/api/v1/tags/untagged/bookmarks?limit=10&offset=1", jsonPath("$.id", is(nullValue())))
                .andDo(TagBookmarkDocumentation.findUntaggedBookmarks());
    }

    @DisplayName("addBookmarkOnTag: 태그에 하나의 북마크를 추가한다.")
    @Test
    void addBookmarkOnTag() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);

        createByPathVariables(user, "/api/v1/tags/{tagId}/bookmarks/{bookmarkId}", tag.getId(), bookmark.getId())
                .andDo(TagBookmarkDocumentation.addBookmarkOnTag());
    }

    @DisplayName("removeBookmarkOnTag: 태그에 속해있는 하나의 북마크를 삭제한다.")
    @Test
    void removeBookmarkOnTag() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        removeByPathVariables(user, "/api/v1/tags/{tagId}/bookmarks/{bookmarkId}", tag.getId(), bookmark.getId())
                .andDo(TagBookmarkDocumentation.removeBookmarkOnTag());
    }

    @DisplayName("findTagsByBookmarkId: 하나의 북마크에 포함된 태그목록을 조회한다.")
    @Test
    void findTagsByBookmarkId() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save(user);
        final Tag tag = tagSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        readByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}/tags", bookmark.getId())
                .andExpect(jsonPath("$.id", is(bookmark.getId().intValue())))
                .andDo(TagBookmarkDocumentation.findTagsByBookmarkId());
    }
}
