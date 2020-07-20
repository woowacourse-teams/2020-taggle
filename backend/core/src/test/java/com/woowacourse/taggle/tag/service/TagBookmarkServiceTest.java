package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
import com.woowacourse.taggle.tag.domain.TagBookmarkRepository;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.BookmarkAddRequest;
import com.woowacourse.taggle.tag.dto.BookmarkRemoveRequest;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
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

    @Autowired
    private TagBookmarkRepository tagBookmarkRepository;

    @DisplayName("createTag: 태그를 생성한다")
    @Test
    void createTag() {
        System.out.println(tagBookmarkService);

        // given
        String tagName = "tag";

        TagCreateRequest tagCreateRequest = new TagCreateRequest(tagName);

        // when
        TagResponse tag = tagBookmarkService.createTag(tagCreateRequest);

        // then
        assertAll(
                () -> assertThat(tag.getId()).isNotNull(),
                () -> assertThat(tag.getName()).isEqualTo(tagName)
        );
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @DisplayName("addBookmark: 태그에 북마크를 추가한다")
    @Test
    void addBookmark() {
        // given
        TagResponse tagResponse = tagBookmarkService.createTag(new TagCreateRequest("taggle"));
        BookmarkAddRequest bookmarkAddRequest1 = new BookmarkAddRequest(tagResponse.getId(),
                "https://github.com/taggle");
        BookmarkAddRequest bookmarkAddRequest2 = new BookmarkAddRequest(tagResponse.getId(),
                "https://github.com/ks-kim");

        // when
        tagBookmarkService.addBookmarkOnTag(bookmarkAddRequest1);
        tagBookmarkService.addBookmarkOnTag(bookmarkAddRequest2);
        Tag tag = tagRepository.findById(tagResponse.getId()).get();
        Bookmark bookmark1 = bookmarkRepository.findByUrl("https://github.com/taggle").get();
        Bookmark bookmark2 = bookmarkRepository.findByUrl("https://github.com/ks-kim").get();

        // then
        assertThat(bookmark1.getTags()).hasSize(1);
        assertThat(bookmark2.getTags()).hasSize(1);
        assertThat(tag.getBookmarks()).hasSize(2);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @DisplayName("addBookmark: 태그에 북마크를 추가할 때 이미 존재하는 북마크인 경우 추가하지 않는다")
    @Test
    void addBookmark_DuplicateBookmark_NotSave() {
        // given
        TagResponse tagResponse = tagBookmarkService.createTag(new TagCreateRequest("taggle"));
        BookmarkAddRequest bookmarkAddRequest1 = new BookmarkAddRequest(tagResponse.getId(),
                "https://github.com/taggle");
        BookmarkAddRequest bookmarkAddRequest2 = new BookmarkAddRequest(tagResponse.getId(),
                "https://github.com/ks-kim");

        // when
        tagBookmarkService.addBookmarkOnTag(bookmarkAddRequest1);
        tagBookmarkService.addBookmarkOnTag(bookmarkAddRequest1);
        tagBookmarkService.addBookmarkOnTag(bookmarkAddRequest2);
        tagBookmarkService.addBookmarkOnTag(bookmarkAddRequest2);
        Tag tag = tagRepository.findById(tagResponse.getId()).get();
        Bookmark bookmark1 = bookmarkRepository.findByUrl("https://github.com/taggle").get();
        Bookmark bookmark2 = bookmarkRepository.findByUrl("https://github.com/ks-kim").get();

        // then
        assertThat(bookmark1.getTags()).hasSize(1);
        assertThat(bookmark2.getTags()).hasSize(1);
        assertThat(tag.getBookmarks()).hasSize(2);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @DisplayName("removeBookmark: 태그에 존재하는 북마크를 제거한다")
    @Test
    void removeBookmark() {
        // given
        TagResponse tagResponse = tagBookmarkService.createTag(new TagCreateRequest("taggle"));
        BookmarkAddRequest bookmarkAddRequest1 =
                new BookmarkAddRequest(tagResponse.getId(), "https://github.com/taggle");
        BookmarkAddRequest bookmarkAddRequest2 =
                new BookmarkAddRequest(tagResponse.getId(), "https://github.com/ks-kim");
        tagBookmarkService.addBookmarkOnTag(bookmarkAddRequest1);
        tagBookmarkService.addBookmarkOnTag(bookmarkAddRequest2);
        Tag addedTag = tagRepository.findById(tagResponse.getId()).get();
        Bookmark addedBookmark1 = bookmarkRepository.findByUrl("https://github.com/taggle").get();
        Bookmark addedBookmark2 = bookmarkRepository.findByUrl("https://github.com/ks-kim").get();
        BookmarkRemoveRequest bookmarkRemoveRequest1 =
                new BookmarkRemoveRequest(addedTag.getId(), addedBookmark1.getId());
        BookmarkRemoveRequest bookmarkRemoveRequest2 =
                new BookmarkRemoveRequest(addedTag.getId(), addedBookmark2.getId());

        // when
        tagBookmarkService.removeBookmarkOnTag(bookmarkRemoveRequest1);
        tagBookmarkService.removeBookmarkOnTag(bookmarkRemoveRequest2);
        Tag tag = tagRepository.findById(tagResponse.getId()).get();
        Bookmark bookmark1 = bookmarkRepository.findByUrl("https://github.com/taggle").get();
        Bookmark bookmark2 = bookmarkRepository.findByUrl("https://github.com/ks-kim").get();

        // then
        assertThat(tag.getBookmarks()).hasSize(0);
        assertThat(bookmark1.getTags()).hasSize(0);
        assertThat(bookmark2.getTags()).hasSize(0);
    }
}
