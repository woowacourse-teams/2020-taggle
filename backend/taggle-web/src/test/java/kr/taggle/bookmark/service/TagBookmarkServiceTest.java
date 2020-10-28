package kr.taggle.bookmark.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.dto.BookmarkCreateDto;
import kr.taggle.bookmark.dto.BookmarkCreateRequest;
import kr.taggle.bookmark.dto.BookmarkDetailResponse;
import kr.taggle.bookmark.dto.BookmarkPageRequest;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.bookmark.dto.TagDetailResponse;
import kr.taggle.bookmark.exception.BookmarkNotFoundException;
import kr.taggle.bookmark.exception.TagBookmarkNotFoundException;
import kr.taggle.tag.dto.TagCreateRequest;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.tag.exception.TagNotFoundException;
import kr.taggle.tag.service.TagService;
import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;

@Transactional
@SpringBootTest
class TagBookmarkServiceTest {

    private static final String TAG_NAME = "spring boot";
    private static final BookmarkPageRequest BOOKMARK_FIND_REQUEST = new BookmarkPageRequest(1, 10);

    @Autowired
    private TagBookmarkService tagBookmarkService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UserService userService;

    private SessionUser user;

    @BeforeEach
    void setUp() {
        final User testUser = userService.save(User.builder()
                .email("jordyLover@kakao.com")
                .notificationEmail("jordyLover@kakao.com")
                .nickName("tigger")
                .role(Role.USER)
                .picture("https://www.naver.com/")
                .notificationEnabled(false)
                .build());
        user = new SessionUser(testUser);
    }

    @DisplayName("createTagBookmark: 태그에 북마크를 추가한다.")
    @Test
    void createTagBookmark() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest(TAG_NAME);
        final TagResponse tag = tagService.createTag(user, tagCreateRequest);

        final BookmarkCreateRequest bookmarkCreateRequest = new BookmarkCreateRequest(
                "https://github.com/woowacourse-teams/2020-taggle");
        final BookmarkCreateDto bookmarkCreateDto = BookmarkCreateDto.of(bookmarkCreateRequest, "title", "description",
                "image");
        final BookmarkResponse bookmark = bookmarkService.createBookmark(user, bookmarkCreateDto);

        tagBookmarkService.createTagBookmark(user, bookmark.getId(), tag.getId());

        // when
        final TagDetailResponse tagDetailResponse = bookmarkService.findBookmarksByTagId(user, tag.getId(),
                BOOKMARK_FIND_REQUEST);

        // then
        assertThat(tagDetailResponse.getName()).isEqualTo(TAG_NAME);
        assertThat(tagDetailResponse.getBookmarks()).hasSize(1);
    }

    @DisplayName("createTagBookmark_Untagged: Untagged의 북마크를 조회한다.")
    @Test
    void createTagBookmark_Untagged() {
        // given
        final BookmarkCreateRequest bookmarkCreateRequest = new BookmarkCreateRequest(
                "https://github.com/woowacourse-teams/2020-taggle");
        final BookmarkCreateDto bookmarkCreateDto = BookmarkCreateDto.of(bookmarkCreateRequest, "title", "description",
                "image");
        bookmarkService.createBookmark(user, bookmarkCreateDto);

        // when
        final TagDetailResponse untaggedBookmarks = bookmarkService.findUntaggedBookmarks(user,
                BOOKMARK_FIND_REQUEST);

        // then
        assertThat(untaggedBookmarks.getId()).isNull();
        assertThat(untaggedBookmarks.getName()).isEqualTo("Untagged");
        assertThat(untaggedBookmarks.getBookmarks()).hasSize(1);
    }

    @DisplayName("removeTagBookmark: 태그에 있는 북마크를 삭제한다.")
    @Test
    void removeTagBookmark() {
        // given
        final TagResponse taggle = tagService.createTag(user, new TagCreateRequest("taggle"));

        final BookmarkResponse bookmark1 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/1", "title",
                        "description", "image"));
        final BookmarkResponse bookmark2 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/2", "title",
                        "description", "image"));

        tagBookmarkService.createTagBookmark(user, bookmark1.getId(), taggle.getId());
        tagBookmarkService.createTagBookmark(user, bookmark2.getId(), taggle.getId());

        // when
        tagBookmarkService.removeTagBookmark(user, bookmark1.getId(), taggle.getId());
        final TagDetailResponse tagBookmark = bookmarkService.findBookmarksByTagId(user, taggle.getId(),
                BOOKMARK_FIND_REQUEST);

        // then
        assertThat(tagBookmark.getBookmarks()).hasSize(1);
    }

    @DisplayName("removeTagBookmark: 태그에 있는 북마크를 삭제한다.")
    @Test
    void removeTagBookmark_TagNotFound_ExceptionThrown() {
        // given
        final TagResponse taggle = tagService.createTag(user, new TagCreateRequest("taggle"));

        final BookmarkResponse bookmark1 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/1", "title",
                        "description", "image"));

        tagBookmarkService.createTagBookmark(user, bookmark1.getId(), taggle.getId());

        // when // then
        assertThatThrownBy(() -> tagService.removeTag(user, 0L))
                .isInstanceOf(TagNotFoundException.class)
                .hasMessageContaining("태그가 존재하지 않습니다.");
    }

    @Test
    void removeTagBookmark_BookmarkNotFound_ExceptionThrown() {
        // given
        final TagResponse taggle = tagService.createTag(user, new TagCreateRequest("taggle"));

        final BookmarkResponse bookmark1 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/1", "title",
                        "description", "image"));

        tagBookmarkService.createTagBookmark(user, bookmark1.getId(), taggle.getId());
        final Long tagId = taggle.getId();

        // when // then
        assertThatThrownBy(() -> tagBookmarkService.removeTagBookmark(user, 0L, tagId))
                .isInstanceOf(BookmarkNotFoundException.class)
                .hasMessageContaining("북마크가 존재하지 않습니다.");
    }

    @Test
    void removeTagBookmark_TagBookmarkNotFound_ExceptionThrown() {
        // given
        final TagResponse taggle1 = tagService.createTag(user, new TagCreateRequest("taggle1"));
        final TagResponse taggle2 = tagService.createTag(user, new TagCreateRequest("taggle2"));

        final BookmarkResponse bookmark1 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/1", "title",
                        "description", "image"));
        final BookmarkResponse bookmark2 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/2", "title",
                        "description", "image"));

        tagBookmarkService.createTagBookmark(user, bookmark1.getId(), taggle1.getId());
        tagBookmarkService.createTagBookmark(user, bookmark2.getId(), taggle2.getId());
        final Long bookmarkId = bookmark1.getId();
        final Long tagId = taggle2.getId();


        // when // then
        assertThatThrownBy(() -> tagBookmarkService.removeTagBookmark(user, bookmarkId, tagId))
                .isInstanceOf(TagBookmarkNotFoundException.class)
                .hasMessageContaining("태그에 추가된 북마크를 찾을 수 없습니다.");
    }
}
