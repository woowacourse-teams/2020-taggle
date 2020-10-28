package kr.taggle.bookmark.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.taggle.ControllerTest;
import kr.taggle.bookmark.docs.TagBookmarkDocumentation;
import kr.taggle.bookmark.service.TagBookmarkService;
import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;

class TagBookmarkControllerTest extends ControllerTest {

    @MockBean
    TagBookmarkService tagBookmarkService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "User","User@gmail.com","notiUser@gamil.com", null, "picture", Boolean.FALSE, Role.USER);
    }

    @DisplayName("createTagBookmark: 태그에 하나의 북마크를 추가한다.")
    @Test
    void createTagBookmark() throws Exception {

        createByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}/tags/{tagId}", 1L, 1L)
                .andDo(TagBookmarkDocumentation.createTagBookmark());
    }
    //
    @DisplayName("removeTagBookmark: 태그에 속해있는 하나의 북마크를 삭제한다.")
    @Test
    void removeTagBookmark() throws Exception {
        User user = new User(1L, "User","User@gmail.com","notiUser@gamil.com", null, "picture", Boolean.FALSE, Role.USER);

        removeByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}/tags/{tagId}", 1L,1L)
                .andDo(TagBookmarkDocumentation.removeTagBookmark());
    }
}
