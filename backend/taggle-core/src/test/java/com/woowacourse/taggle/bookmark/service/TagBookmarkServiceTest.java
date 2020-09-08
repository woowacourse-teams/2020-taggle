package com.woowacourse.taggle.bookmark.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.bookmark.dto.BookmarkCreateDto;
import com.woowacourse.taggle.bookmark.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.bookmark.dto.BookmarkPageRequest;
import com.woowacourse.taggle.bookmark.dto.BookmarkResponse;
import com.woowacourse.taggle.bookmark.dto.BookmarkTagResponse;
import com.woowacourse.taggle.bookmark.dto.TagBookmarkResponse;
import com.woowacourse.taggle.fixture.UserFixture;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import com.woowacourse.taggle.tag.service.TagService;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;

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

        tagBookmarkService.createTagBookmark(user, tag.getId(), bookmark.getId());

        // when
        final TagBookmarkResponse tagBookmarkResponse = tagBookmarkService.findBookmarksByTagId(user, tag.getId(),
                BOOKMARK_FIND_REQUEST);

        // then
        assertThat(tagBookmarkResponse.getName()).isEqualTo(TAG_NAME);
        assertThat(tagBookmarkResponse.getBookmarks()).hasSize(1);
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
        final TagBookmarkResponse untaggedBookmarks = tagBookmarkService.findUntaggedBookmarks(user,
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
        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark1.getId());
        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark2.getId());
        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark3.getId());
        tagBookmarkService.createTagBookmark(user, naver.getId(), bookmark1.getId());
        tagBookmarkService.createTagBookmark(user, google.getId(), bookmark2.getId());
        tagBookmarkService.createTagBookmark(user, google.getId(), bookmark3.getId());

        final TagBookmarkResponse tagBookmark1 = tagBookmarkService.findBookmarksByTagId(user, taggle.getId(),
                BOOKMARK_FIND_REQUEST);
        final TagBookmarkResponse tagBookmark2 = tagBookmarkService.findBookmarksByTagId(user, google.getId(),
                BOOKMARK_FIND_REQUEST);
        final TagBookmarkResponse tagBookmark3 = tagBookmarkService.findBookmarksByTagId(user, naver.getId(),
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

        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark1.getId());
        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark2.getId());

        // when
        tagBookmarkService.removeTagBookmark(user, taggle.getId(), bookmark1.getId());
        final TagBookmarkResponse tagBookmark = tagBookmarkService.findBookmarksByTagId(user, taggle.getId(),
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

        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark1.getId());
        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark2.getId());

        // when
        tagService.removeTag(user, taggle.getId());

        // then
        assertThatThrownBy(() -> tagBookmarkService.findBookmarksByTagId(user, taggle.getId(), BOOKMARK_FIND_REQUEST))
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
        final BookmarkTagResponse expected = tagBookmarkService.findTagsByBookmarkId(user, bookmark.getId());

        // then
        assertThat(expected.getId()).isEqualTo(bookmark.getId());
    }
}
