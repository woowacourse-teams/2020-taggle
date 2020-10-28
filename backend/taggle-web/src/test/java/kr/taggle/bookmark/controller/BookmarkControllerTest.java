package kr.taggle.bookmark.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.taggle.ControllerTest;
import kr.taggle.bookmark.docs.BookmarkDocumentation;
import kr.taggle.bookmark.dto.BookmarkDetailResponse;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.bookmark.dto.TagDetailResponse;
import kr.taggle.bookmark.service.BookmarkCreateService;
import kr.taggle.bookmark.service.BookmarkService;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;

class BookmarkControllerTest extends ControllerTest {

    @MockBean
    BookmarkCreateService bookmarkCreateService;

    @MockBean
    BookmarkService bookmarkService;

    private User user;

    @BeforeEach
    void setUp() {
        //builder수정
        user = new User(1L, "User","User@gmail.com","notiUser@gamil.com", null, "picture", Boolean.FALSE, Role.USER);
    }

    @DisplayName("createBookmark: 북마크를 추가한다.")
    @Test
    void createBookmark() throws Exception {

        BookmarkResponse bookmarkResponse = new BookmarkResponse(1L, "http://www.taggle.kr", "taggle", "description", "image");

        when(bookmarkCreateService.createBookmark(any(), any())).thenReturn(bookmarkResponse);

        createByJsonParams(user, "/api/v1/bookmarks", "{ \"url\": \"http://github.com\" }",
                jsonPath("$.title", Is.is("taggle")))
                .andDo(BookmarkDocumentation.createBookmark());
    }

    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() throws Exception {
        List<BookmarkResponse> bookmarkResponses = Arrays.asList(
                new BookmarkResponse(1L, "https://taggle.kr", "title", "description", "image")
        , new BookmarkResponse(2L, "https://www.naver.com", "naver","desc", "naverImage"));
        when(bookmarkService.findBookmarks(any(), any())).thenReturn(bookmarkResponses);
        read(user, "/api/v1/bookmarks?offset=1&limit=10", jsonPath("$", hasSize(2)))
                .andDo(BookmarkDocumentation.findBookmarks());
    }

    @DisplayName("findBookmarkDetail: 북마크 단건을 조회한다.")
    @Test
    void findBookmarkDetail() throws Exception {
        List<TagResponse> tags = Arrays.asList(new TagResponse(1L, "Tag"));
        BookmarkDetailResponse bookmarkDetailResponse = new BookmarkDetailResponse(1L, "https://taggle.kr", tags);
        when(bookmarkService.findTagsByBookmarkId(any(), any())).thenReturn(bookmarkDetailResponse);
        readByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}", bookmarkDetailResponse.getId())
                .andExpect(jsonPath("$.id", is(bookmarkDetailResponse.getId().intValue())))
                .andDo(BookmarkDocumentation.findBookmarkDetail());
    }

    @DisplayName("findBookmarksByTagId: 특정 단건 태그를 포함하는 북마크 목록을 조회한다.")
    @Test
    void findBookmarksByTagId() throws Exception {
        List<BookmarkResponse> bookmarkResponses = Arrays.asList(
                new BookmarkResponse(1L, "https://taggle.kr", "taggle", "desc", "image"));
        TagDetailResponse tagDetailResponse = new TagDetailResponse(1L, "TagDetail", bookmarkResponses);
        when(bookmarkService.findBookmarksByTagId(any(), any(), any())).thenReturn(tagDetailResponse);
        read(user, String.format("/api/v1/bookmarks?tag=%d&offset=1&limit=10", 1L),
                jsonPath("$.bookmarks.[0].title", is("taggle")))
                .andDo(BookmarkDocumentation.findBookmarksByTagId());

    }

    @DisplayName("findBookmarksWithUntagged: 태그가 없는 북마크 목록을 조회한다.")
    @Test
    void findBookmarksWithUntagged() throws Exception {
        List<BookmarkResponse> bookmarkResponses = Arrays.asList(
                new BookmarkResponse(1L, "https://taggle.kr", "taggle", "desc", "image"));
        TagDetailResponse tagDetailResponse = new TagDetailResponse(null, "TagDetail", bookmarkResponses);

        when(bookmarkService.findUntaggedBookmarks(any(), any())).thenReturn(tagDetailResponse);

        read(user, "/api/v1/bookmarks?tag=none&limit=10&offset=1", jsonPath("$.id", is(nullValue())))
                .andDo(BookmarkDocumentation.findUntaggedBookmarks());
    }

    @DisplayName("removeBookmark: 북마크 하나를 제거한다.")
    @Test
    void removeBookmark() throws Exception {

        removeByPathVariables(user, "/api/v1/bookmarks/{bookmarkId}", 1L)
                .andDo(BookmarkDocumentation.removeBookmark());
    }
}
