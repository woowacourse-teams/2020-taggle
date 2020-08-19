package com.woowacourse.taggle.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import com.woowacourse.taggle.tag.domain.TagBookmarkRepository;
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
    public TagBookmarkResponse findBookmarksOfTag(final SessionUser user, final Long tagId) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());

        return TagBookmarkResponse.of(tag);
    }

    @Transactional(readOnly = true)
    public TagBookmarkResponse findBookmarksOfUntagged(final SessionUser user) {
        final List<Bookmark> bookmarks = bookmarkService.findAllByUserId(user.getId()).stream()
                .filter(Bookmark::isTagsEmpty)
                .collect(Collectors.toList());
        return TagBookmarkResponse.ofUntagged(bookmarks);
    }

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

    @Transactional(readOnly = true)
    public BookmarkTagResponse findTagsOfBookmark(final SessionUser user, final Long id) {
        final Bookmark bookmark = bookmarkService.findByIdAndUserId(id, user.getId());

        return BookmarkTagResponse.of(bookmark);
    }
}
