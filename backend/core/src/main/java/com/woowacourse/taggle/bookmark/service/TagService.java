package com.woowacourse.taggle.bookmark.service;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.bookmark.domain.Bookmark;
import com.woowacourse.taggle.bookmark.domain.BookmarkRepository;
import com.woowacourse.taggle.bookmark.domain.Tag;
import com.woowacourse.taggle.bookmark.domain.TagRepository;
import com.woowacourse.taggle.bookmark.dto.BookmarkAddRequest;
import com.woowacourse.taggle.bookmark.dto.BookmarkRemoveRequest;
import com.woowacourse.taggle.bookmark.dto.TagCreateRequest;
import com.woowacourse.taggle.bookmark.dto.TagResponse;
import com.woowacourse.taggle.bookmark.exception.BookmarkNotFoundException;
import com.woowacourse.taggle.bookmark.exception.DuplicateTagException;
import com.woowacourse.taggle.bookmark.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final BookmarkRepository bookmarkRepository;

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
        tag.addBookmark(bookmark);
        tagRepository.save(tag);
    }

    public void removeBookmarkOnTag(final BookmarkRemoveRequest bookmarkRemoveRequest) {
        Tag tag = tagRepository.findById(bookmarkRemoveRequest.getTagId())
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다."
                        + "tagId: " + bookmarkRemoveRequest.getTagId()));
        Bookmark bookmark = bookmarkRepository.findById(bookmarkRemoveRequest.getBookmarkId())
                .orElseThrow(() -> new BookmarkNotFoundException("북마크가 존재하지 않습니다."
                        + "bookmarkId: " + bookmarkRemoveRequest.getBookmarkId()));
        tag.removeBookmark(bookmark);
        tagRepository.save(tag);
    }
}
