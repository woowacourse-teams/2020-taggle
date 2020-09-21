package kr.taggle.bookmark.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.taggle.ControllerTest;
import kr.taggle.bookmark.docs.BookmarkDocumentation;
import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.setup.domain.BookmarkSetup;
import kr.taggle.setup.domain.TagBookmarkSetup;
import kr.taggle.setup.domain.TagSetup;
import kr.taggle.setup.domain.UserSetup;
import kr.taggle.tag.domain.Tag;
import kr.taggle.user.domain.User;

class BookmarkControllerTest extends ControllerTest {

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @Autowired
    private TagBookmarkSetup tagBookmarkSetup;

    @Autowired
    private TagSetup tagSetup;

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

    @DisplayName("findBookmarkDetail: 북마크 단건을 조회한다.")
    @Test
    void findBookmarkDetail() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save(user);
        final Tag tag = tagSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        readByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}", bookmark.getId())
                .andExpect(jsonPath("$.id", is(bookmark.getId().intValue())))
                .andDo(BookmarkDocumentation.findBookmarkDetail());
    }

    @DisplayName("findBookmarksByTagId: 특정 단건 태그를 포함하는 북마크 목록을 조회한다.")
    @Test
    void findBookmarksByTagId() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);

        tagBookmarkSetup.save(tag, bookmark);
        read(user, String.format("/api/v1/bookmarks?tag=%d&limit=10&offset=1", tag.getId()),
                jsonPath("$.id", is(tag.getId().intValue())))
                .andDo(BookmarkDocumentation.findBookmarksByTagId());

    }

    @DisplayName("findBookmarksWithUntagged: 태그가 없는 북마크 목록을 조회한다.")
    @Test
    void findBookmarksWithUntagged() throws Exception {
        bookmarkSetup.save(user);

        read(user, "/api/v1/bookmarks?tag=none&limit=10&offset=1", jsonPath("$.id", is(nullValue())))
                .andDo(BookmarkDocumentation.findUntaggedBookmarks());
    }

    @DisplayName("removeBookmark: 북마크 하나를 제거한다.")
    @Test
    void removeBookmark() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save(user);

        removeByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}", bookmark.getId())
                .andDo(BookmarkDocumentation.removeBookmark());
    }
}
