package com.woowacourse.taggle.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import com.woowacourse.taggle.tag.domain.TagBookmarkRepository;
import com.woowacourse.taggle.tag.exception.TagBookmarkNotFoundException;
import com.woowacourse.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TagBookmarkService {

    private final TagService tagService;
    private final BookmarkService bookmarkService;
    private final TagBookmarkRepository tagBookmarkRepository;

    public void createTagBookmark(final SessionUser user, final Long tagId, final Long bookmarkId) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
        final Bookmark bookmark = bookmarkService.findByIdAndUserId(bookmarkId, user.getId());
        final TagBookmark tagBookmark = tagBookmarkRepository.save(new TagBookmark(tag, bookmark));

        tag.addTagBookmark(tagBookmark);
        bookmark.addTagBookmark(tagBookmark);
    }

    public void removeTagBookmark(final SessionUser user, final Long tagId, final Long bookmarkId) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
        final Bookmark bookmark = bookmarkService.findByIdAndUserId(bookmarkId, user.getId());
        final TagBookmark tagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)
                .orElseThrow(() -> new TagBookmarkNotFoundException(
                        "태그북마크를 찾을 수 없습니다. tagId : " + tag + " bookmarkId: " + bookmarkId));

        tagBookmarkRepository.delete(tagBookmark);
        tag.removeTagBookmark(tagBookmark);
        bookmark.removeTagBookmark(tagBookmark);
    }
}
