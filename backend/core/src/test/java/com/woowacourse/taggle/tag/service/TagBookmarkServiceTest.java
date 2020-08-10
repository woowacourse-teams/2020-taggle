package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.TagBookmarkRequest;
import com.woowacourse.taggle.tag.dto.TagRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;

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

    @DisplayName("findTagBookmark: 태그와 태그에 속해있는 북마크 조회한다.")
    @Test
    void findTagBookmark() {
        // given
        final Tag taggle = tagRepository.save(new Tag("taggle"));
        final Bookmark bookmark1 = bookmarkRepository.save(new Bookmark("https://bookmark/1"));
        tagBookmarkService.createTagBookmark(taggle.getId(), bookmark1.getId());

        // when
        final TagResponse tagBookmark = tagBookmarkService.findTagBookmark(new TagRequest(taggle.getId()));

        // then
        assertThat(tagBookmark).isNotNull();
    }

    @DisplayName("createTagBookmark: 태그에 북마크를 추가한다.")
    @Test
    void createTagBookmark() {
        // given
        final Tag taggle = tagRepository.save(new Tag("taggle"));
        final Tag google = tagRepository.save(new Tag("google"));
        final Tag naver = tagRepository.save(new Tag("naver"));
        final Bookmark bookmark1 = bookmarkRepository.save(new Bookmark("https://bookmark/1"));
        final Bookmark bookmark2 = bookmarkRepository.save(new Bookmark("https://bookmark/2"));
        final Bookmark bookmark3 = bookmarkRepository.save(new Bookmark("https://bookmark/3"));
        tagBookmarkService.createTagBookmark(taggle.getId(), bookmark1.getId());
        tagBookmarkService.createTagBookmark(taggle.getId(), bookmark2.getId());
        tagBookmarkService.createTagBookmark(taggle.getId(), bookmark3.getId());
        tagBookmarkService.createTagBookmark(naver.getId(), bookmark1.getId());
        tagBookmarkService.createTagBookmark(google.getId(), bookmark2.getId());
        tagBookmarkService.createTagBookmark(google.getId(), bookmark3.getId());

        // when
        final TagResponse tagBookmark = tagBookmarkService.findTagBookmark(new TagRequest(taggle.getId()));

        // then
        assertThat(tagBookmark.getId()).isEqualTo(taggle.getId());
        assertThat(tagBookmark.getName()).isEqualTo(taggle.getName());
        // assertThat(tagBookmark.getBookmarks()).hasSize(3);
        // assertThat(tagBookmark.getBookmarks().get(0).getTagNames()).hasSize(2);
        // assertThat(tagBookmark.getBookmarks().get(1).getTagNames()).hasSize(2);
        // assertThat(tagBookmark.getBookmarks().get(0).getTagNames()).hasSize(2);
    }

    @DisplayName("removeTagBookmark: 태그에 있는 북마크를 삭제한다.")
    @Test
    void removeTagBookmark() {
        // given
        final Tag tag = tagRepository.save(new Tag("taggle"));
        final Bookmark bookmark = bookmarkRepository.save(new Bookmark("https://bookmark/1"));
        tagBookmarkService.createTagBookmark(tag.getId(), bookmark.getId());
        final TagBookmarkRequest tagBookmarkRequest = new TagBookmarkRequest(tag.getId(), bookmark.getId());

        // when
        tagBookmarkService.removeTagBookmark(tagBookmarkRequest);
        final TagResponse tagBookmark = tagBookmarkService.findTagBookmark(new TagRequest(tag.getId()));

        // then
        assertThat(tagBookmark).isNotNull();
        // assertThat(tagBookmark.getBookmarks()).hasSize(0);
    }
}
