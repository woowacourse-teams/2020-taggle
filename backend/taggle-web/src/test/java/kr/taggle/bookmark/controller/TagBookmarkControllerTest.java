package kr.taggle.bookmark.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.taggle.ControllerTest;
import kr.taggle.bookmark.docs.TagBookmarkDocumentation;
import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.setup.domain.BookmarkSetup;
import kr.taggle.setup.domain.TagBookmarkSetup;
import kr.taggle.setup.domain.TagSetup;
import kr.taggle.setup.domain.UserSetup;
import kr.taggle.tag.domain.Tag;
import kr.taggle.user.domain.User;

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
        read(user, String.format("/api/v1/bookmarks?tag=%d&limit=10&offset=1", tag.getId()), jsonPath("$.id", is(tag.getId().intValue())))
                .andDo(TagBookmarkDocumentation.findBookmarksByTagId());

    }

    @DisplayName("findBookmarksWithUntagged: 태그가 없는 북마크 목록을 조회한다.")
    @Test
    void findBookmarksWithUntagged() throws Exception {
        bookmarkSetup.save(user);

        read(user, "/api/v1/bookmarks?tag=none&limit=10&offset=1", jsonPath("$.id", is(nullValue())))
                .andDo(TagBookmarkDocumentation.findUntaggedBookmarks());
    }

    @DisplayName("createTagBookmark: 태그에 하나의 북마크를 추가한다.")
    @Test
    void createTagBookmark() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);

        createByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}/tags/{tagId}", bookmark.getId(), tag.getId())
                .andDo(TagBookmarkDocumentation.createTagBookmark());
    }

    @DisplayName("removeTagBookmark: 태그에 속해있는 하나의 북마크를 삭제한다.")
    @Test
    void removeTagBookmark() throws Exception {
        final Tag tag = tagSetup.save(user);
        final Bookmark bookmark = bookmarkSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        removeByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}/tags/{tagId}", bookmark.getId(), tag.getId())
                .andDo(TagBookmarkDocumentation.removeTagBookmark());
    }

    @DisplayName("findBookmarkDetail: 하나의 북마크에 포함된 태그목록을 조회한다.")
    @Test
    void findBookmarkDetail() throws Exception {
        final Bookmark bookmark = bookmarkSetup.save(user);
        final Tag tag = tagSetup.save(user);
        tagBookmarkSetup.save(tag, bookmark);

        readByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}", bookmark.getId())
                .andExpect(jsonPath("$.id", is(bookmark.getId().intValue())))
                .andDo(TagBookmarkDocumentation.findBookmarkDetail());
    }
}
