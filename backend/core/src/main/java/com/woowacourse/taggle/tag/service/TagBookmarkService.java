package com.woowacourse.taggle.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import com.woowacourse.taggle.tag.domain.TagBookmarkRepository;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.TagBookmarkRequest;
import com.woowacourse.taggle.tag.dto.TagRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;
import com.woowacourse.taggle.tag.exception.TagBookmarkNotFoundException;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagBookmarkService {

    private final TagRepository tagRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TagBookmarkRepository tagBookmarkRepository;

    @Transactional(readOnly = true)
    public TagResponse findTagBookmark(final TagRequest tagRequest) {
        Tag tag = tagRepository.findById(tagRequest.getId())
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다.\n"
                        + "tagId: " + tagRequest.getId()));
        return TagResponse.of(tag);
    }

    @Transactional
    public void createTagBookmark(final TagBookmarkRequest tagBookmarkRequest) {
        Tag tag = tagRepository.findById(tagBookmarkRequest.getTagId())
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다.\n"
                        + "tagId: " + tagBookmarkRequest.getTagId()));
        Bookmark bookmark = bookmarkRepository.findById(tagBookmarkRequest.getBookmarkId())
                .orElseThrow(() -> new BookmarkNotFoundException("북마크가 존재하지 않습니다.\n"
                        + "bookmarkId: " + tagBookmarkRequest.getBookmarkId()));
        TagBookmark tagBookmark = tagBookmarkRepository.save(new TagBookmark(tag, bookmark));

        tag.addTagBookmark(tagBookmark);
        bookmark.addTagBookmark(tagBookmark);
    }

    @Transactional
    public void removeTagBookmark(final TagBookmarkRequest tagBookmarkRequest) {
        Tag tag = tagRepository.findById(tagBookmarkRequest.getTagId())
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다.\n"
                        + "tagId: " + tagBookmarkRequest.getTagId()));
        Bookmark bookmark = bookmarkRepository.findById(tagBookmarkRequest.getBookmarkId())
                .orElseThrow(() -> new BookmarkNotFoundException("북마크가 존재하지 않습니다.\n"
                        + "bookmarkId: " + tagBookmarkRequest.getBookmarkId()));
        TagBookmark tagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)
                .orElseThrow(() -> new TagBookmarkNotFoundException("태그에 북마크가 존재하지 않습니다.\n"
                        + "tagId:" + tagBookmarkRequest.getTagId() + "\n"
                        + "bookmarkId: " + tagBookmarkRequest.getBookmarkId()));

        tag.removeTagBookmark(tagBookmark);
        bookmark.removeTagBookmark(tagBookmark);
    }
}
