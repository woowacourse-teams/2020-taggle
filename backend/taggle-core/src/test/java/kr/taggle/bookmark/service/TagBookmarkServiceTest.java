package kr.taggle.bookmark.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taggle.JpaTestConfiguration;
import kr.taggle.bookmark.dto.BookmarkCreateDto;
import kr.taggle.bookmark.dto.BookmarkCreateRequest;
import kr.taggle.bookmark.dto.BookmarkPageRequest;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.bookmark.dto.BookmarkDetailResponse;
import kr.taggle.bookmark.dto.TagDetailResponse;
import kr.taggle.fixture.UserFixture;
import kr.taggle.tag.dto.TagCreateRequest;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.tag.exception.TagNotFoundException;
import kr.taggle.tag.service.TagService;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
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
        final User testUser = userService.save(UserFixture.DEFAULT_USER);
        user = new SessionUser(testUser);
    }

    @DisplayName("findBookmarksByTagId: 특정 태그의 북마크를 조회한다.")
    @Test
    void findBookmarksByTagId() {
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

    @DisplayName("findUntaggedBookmarks: Untagged의 북마크를 조회한다.")
    @Test
    void findUntaggedBookmarks() {
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

    @DisplayName("createTagBookmark: 태그에 북마크를 추가한다.")
    @Test
    void createTagBookmark() {
        // given
        final TagResponse taggle = tagService.createTag(user, new TagCreateRequest("taggle"));
        final TagResponse google = tagService.createTag(user, new TagCreateRequest("google"));
        final TagResponse naver = tagService.createTag(user, new TagCreateRequest("naver"));

        final BookmarkResponse bookmark1 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/1", "title",
                        "description", "image"));
        final BookmarkResponse bookmark2 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/2", "title",
                        "description", "image"));
        final BookmarkResponse bookmark3 = bookmarkService.createBookmark(user,
                new BookmarkCreateDto("https://taggle.co.kr/3", "title",
                        "description", "image"));

        // when
        tagBookmarkService.createTagBookmark(user, bookmark1.getId(), taggle.getId());
        tagBookmarkService.createTagBookmark(user, bookmark2.getId(), taggle.getId());
        tagBookmarkService.createTagBookmark(user, bookmark3.getId(), taggle.getId());
        tagBookmarkService.createTagBookmark(user, bookmark1.getId(), naver.getId());
        tagBookmarkService.createTagBookmark(user, bookmark2.getId(), google.getId());
        tagBookmarkService.createTagBookmark(user, bookmark3.getId(), google.getId());

        final TagDetailResponse tagBookmark1 = bookmarkService.findBookmarksByTagId(user, taggle.getId(),
                BOOKMARK_FIND_REQUEST);
        final TagDetailResponse tagBookmark2 = bookmarkService.findBookmarksByTagId(user, google.getId(),
                BOOKMARK_FIND_REQUEST);
        final TagDetailResponse tagBookmark3 = bookmarkService.findBookmarksByTagId(user, naver.getId(),
                BOOKMARK_FIND_REQUEST);

        // then
        assertThat(tagBookmark1.getId()).isEqualTo(taggle.getId());
        assertThat(tagBookmark1.getName()).isEqualTo(taggle.getName());
        assertThat(tagBookmark1.getBookmarks()).hasSize(3);
        assertThat(tagBookmark2.getId()).isEqualTo(google.getId());
        assertThat(tagBookmark2.getName()).isEqualTo(google.getName());
        assertThat(tagBookmark2.getBookmarks()).hasSize(2);
        assertThat(tagBookmark3.getId()).isEqualTo(naver.getId());
        assertThat(tagBookmark3.getName()).isEqualTo(naver.getName());
        assertThat(tagBookmark3.getBookmarks()).hasSize(1);
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
    void removeTag_TagBookmarkRemoved() {
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
        tagService.removeTag(user, taggle.getId());

        // then
        assertThatThrownBy(() -> bookmarkService.findBookmarksByTagId(user, taggle.getId(), BOOKMARK_FIND_REQUEST))
                .isInstanceOf(TagNotFoundException.class)
                .hasMessageContaining("태그가 존재하지 않습니다.");
    }

    @DisplayName("findBookmark: 하나의 북마크를 조회한다.")
    @Test
    void findTagsByBookmarkId() {
        // given
        final BookmarkCreateDto bookmarkCreateRequest = new BookmarkCreateDto(
                "https://github.com/woowacourse-teams/2020-taggle", "title",
                "description", "image");
        final BookmarkResponse bookmark = bookmarkService.createBookmark(user, bookmarkCreateRequest);

        //when
        final BookmarkDetailResponse expected = bookmarkService.findTagsByBookmarkId(user, bookmark.getId());

        // then
        assertThat(expected.getId()).isEqualTo(bookmark.getId());
    }
}
