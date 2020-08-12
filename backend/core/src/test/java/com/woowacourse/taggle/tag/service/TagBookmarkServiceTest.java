package com.woowacourse.taggle.tag.service;

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
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class TagBookmarkServiceTest {

    @Autowired
    private TagBookmarkService tagBookmarkService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

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
                .email("a@a.com")
                .nickName("tigger")
                .role(Role.USER)
                .picture("https://www.naver.com/")
                .build());
        user = new SessionUser(testUser);
    }

    @DisplayName("createTagBookmark: 태그에 북마크를 추가한다.")
    @Test
    void createTagBookmark() {
        // given
        final TagResponse taggle = tagService.createTag(user, new TagCreateRequest("taggle"));
        final TagResponse google = tagService.createTag(user, new TagCreateRequest("google"));
        final TagResponse naver = tagService.createTag(user, new TagCreateRequest("naver"));

        final BookmarkResponse bookmark1 = bookmarkService.createBookmark(user,
                new BookmarkCreateRequest("https://bookmark/1"));
        final BookmarkResponse bookmark2 = bookmarkService.createBookmark(user,
                new BookmarkCreateRequest("https://bookmark/2"));
        final BookmarkResponse bookmark3 = bookmarkService.createBookmark(user,
                new BookmarkCreateRequest("https://bookmark/3"));

        // when
        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark1.getId());
        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark2.getId());
        tagBookmarkService.createTagBookmark(user, taggle.getId(), bookmark3.getId());
        tagBookmarkService.createTagBookmark(user, naver.getId(), bookmark1.getId());
        tagBookmarkService.createTagBookmark(user, google.getId(), bookmark2.getId());
        tagBookmarkService.createTagBookmark(user, google.getId(), bookmark3.getId());

        final TagBookmarkResponse tagBookmark1 = tagService.findTagById(user, taggle.getId());
        final TagBookmarkResponse tagBookmark2 = tagService.findTagById(user, google.getId());
        final TagBookmarkResponse tagBookmark3 = tagService.findTagById(user, naver.getId());

        // then
        assertThat(tagBookmark1.getId()).isEqualTo(taggle.getId());
        assertThat(tagBookmark1.getName()).isEqualTo(taggle.getName());
        assertThat(tagBookmark1.getBookmarks()).hasSize(3);
        // assertThat(tagBookmark.getBookmarks()).hasSize(3);
        // assertThat(tagBookmark.getBookmarks().get(0).getTagNames()).hasSize(2);
        // assertThat(tagBookmark.getBookmarks().get(1).getTagNames()).hasSize(2);
        // assertThat(tagBookmark.getBookmarks().get(0).getTagNames()).hasSize(2);
    }

    // @DisplayName("removeTagBookmark: 태그에 있는 북마크를 삭제한다.")
    // @Test
    // void removeTagBookmark() {
    //     // given
    //     final Tag tag = tagRepository.save(new Tag("taggle"));
    //     final Bookmark bookmark = bookmarkRepository.save(new Bookmark("https://bookmark/1"));
    //     tagBookmarkService.createTagBookmark(tag.getId(), bookmark.getId());
    //     final TagBookmarkRequest tagBookmarkRequest = new TagBookmarkRequest(tag.getId(), bookmark.getId());
    //
    //     // when
    //     tagBookmarkService.removeTagBookmark(tagBookmarkRequest);
    //     final TagResponse tagBookmark = tagBookmarkService.findTagBookmark(new TagRequest(tag.getId()));
    //
    //     // then
    //     assertThat(tagBookmark).isNotNull();
    //     // assertThat(tagBookmark.getBookmarks()).hasSize(0);
    // }
}
