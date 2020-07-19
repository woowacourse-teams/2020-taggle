package com.woowacourse.taggle.tag.service;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import com.woowacourse.taggle.tag.domain.TagBookmarkRepository;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.BookmarkAddRequest;
import com.woowacourse.taggle.tag.dto.BookmarkRemoveRequest;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;
import com.woowacourse.taggle.tag.exception.DuplicateTagException;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TagBookmarkRepository tagBookmarkRepository;

    public TagResponse createTag(final TagCreateRequest tagCreateRequest) {
        tagRepository.findByName(tagCreateRequest.getName())
                .ifPresent(tag -> {
                    throw new DuplicateTagException("태그가 존재합니다.");
                });
        Tag persistTag = tagRepository.save(tagCreateRequest.toEntity());
        return TagResponse.of(persistTag);
    }

    public void addBookmarkOnTag(final BookmarkAddRequest bookmarkAddRequest) {
        Tag tag = tagRepository.findById(bookmarkAddRequest.getTagId())
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다."));
        Bookmark bookmark = bookmarkRepository.findByUrl(bookmarkAddRequest.getUrl())
                .orElseGet(() -> bookmarkRepository.save(new Bookmark(bookmarkAddRequest.getUrl())));
        TagBookmark tagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)
                .orElseGet(() -> tagBookmarkRepository.save(new TagBookmark(tag, bookmark)));

        tag.addTagBookmark(tagBookmark);
        bookmark.addTagBookmark(tagBookmark);
        tagRepository.save(tag);
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmarkOnTag(final BookmarkRemoveRequest bookmarkRemoveRequest) {
        Tag tag = tagRepository.findById(bookmarkRemoveRequest.getTagId())
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다."
                        + "tagId: " + bookmarkRemoveRequest.getTagId()));
        Bookmark bookmark = bookmarkRepository.findById(bookmarkRemoveRequest.getBookmarkId())
                .orElseThrow(() -> new BookmarkNotFoundException("북마크가 존재하지 않습니다."
                        + "bookmarkId: " + bookmarkRemoveRequest.getBookmarkId()));
        TagBookmark tagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)
                .orElseThrow(() -> new BookmarkNotFoundException("태그에 북마크가 존재하지 않습니다."));

        tag.removeTagBookmark(tagBookmark);
        bookmark.removeTagBookmark(tagBookmark);
        tagRepository.save(tag);
        bookmarkRepository.save(bookmark);
        tagBookmarkRepository.delete(tagBookmark);
    }
}
