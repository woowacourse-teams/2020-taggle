package com.woowacourse.taggle.tag.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import com.woowacourse.taggle.tag.domain.TagBookmarkRepository;
import com.woowacourse.taggle.tag.dto.BookmarkFindRequest;
import com.woowacourse.taggle.tag.dto.BookmarkTagResponse;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
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

    @Transactional(readOnly = true)
    public TagBookmarkResponse findBookmarksByTagId(final SessionUser user, final Long tagId,
            final BookmarkFindRequest bookmarkFindRequest) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
        final Page<TagBookmark> bookmarks = tagBookmarkRepository.findAllByTag(tag, bookmarkFindRequest.toPageable());

        return TagBookmarkResponse.of(tag, bookmarks.getContent());
    }

    @Transactional(readOnly = true)
    public TagBookmarkResponse findUntaggedBookmarks(final SessionUser user,
            final BookmarkFindRequest bookmarkFindRequest) {
        final Page<Bookmark> bookmarks = bookmarkService.findUntaggedBookmarksByUserId(user.getId(),
                bookmarkFindRequest.toPageable());
        return TagBookmarkResponse.ofUntagged(bookmarks.getContent());
    }

    public void createTagBookmark(final SessionUser user, final Long tagId, final Long bookmarkId) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
        final Bookmark bookmark = bookmarkService.findByIdAndUserId(bookmarkId, user.getId());
        final TagBookmark tagBookmark = tagBookmarkRepository.save(new TagBookmark(tag, bookmark));

        bookmark.addTagBookmark(tagBookmark);
    }

    public void removeTagBookmark(final SessionUser user, final Long tagId, final Long bookmarkId) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
        final Bookmark bookmark = bookmarkService.findByIdAndUserId(bookmarkId, user.getId());
        final TagBookmark tagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)
                .orElseThrow(() -> new TagBookmarkNotFoundException(
                        "태그에 추가된 북마크를 찾을 수 없습니다. tagId : " + tag + " bookmarkId: " + bookmarkId));

        tagBookmarkRepository.delete(tagBookmark);
        bookmark.removeTagBookmark(tagBookmark);
    }

    @Transactional(readOnly = true)
    public BookmarkTagResponse findTagsByBookmarkId(final SessionUser user, final Long id) {
        final Bookmark bookmark = bookmarkService.findByIdAndUserId(id, user.getId());

        return BookmarkTagResponse.of(bookmark);
    }
}
