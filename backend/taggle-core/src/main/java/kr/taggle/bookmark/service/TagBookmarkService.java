package kr.taggle.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.bookmark.domain.TagBookmark;
import kr.taggle.bookmark.domain.TagBookmarkRepository;
import kr.taggle.bookmark.exception.TagBookmarkNotFoundException;
import kr.taggle.tag.domain.Tag;
import kr.taggle.tag.service.TagService;
import kr.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TagBookmarkService {

    private final TagService tagService;
    private final BookmarkService bookmarkService;
    private final TagBookmarkRepository tagBookmarkRepository;

    // @Transactional(readOnly = true)
    // public TagBookmarkResponse findBookmarksByTagId(final SessionUser user, final Long tagId,
    //         final BookmarkPageRequest bookmarkPageRequest) {
    //     final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
    //     final Page<TagBookmark> bookmarks = tagBookmarkRepository.findAllByTag(tag, bookmarkPageRequest.toPageable());
    //
    //     return TagBookmarkResponse.of(tag, bookmarks.getContent());
    // }
    //
    // @Transactional(readOnly = true)
    // public TagBookmarkResponse findUntaggedBookmarks(final SessionUser user,
    //         final BookmarkPageRequest bookmarkPageRequest) {
    //     final Page<Bookmark> bookmarks = bookmarkService.findUntaggedBookmarksByUserId(user.getId(),
    //             bookmarkPageRequest.toPageable());
    //     return TagBookmarkResponse.ofUntagged(bookmarks.getContent());
    // }
    //
    // @Transactional(readOnly = true)
    // public BookmarkTagResponse findTagsByBookmarkId(final SessionUser user, final Long id) {
    //     final Bookmark bookmark = bookmarkService.findByIdAndUserId(id, user.getId());
    //
    //     return BookmarkTagResponse.of(bookmark);
    // }

    public void createTagBookmark(final SessionUser user, final Long bookmarkId, final Long tagId) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
        final Bookmark bookmark = bookmarkService.findByIdAndUserId(bookmarkId, user.getId());
        final TagBookmark tagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)
                .orElseGet(() -> tagBookmarkRepository.save(new TagBookmark(tag, bookmark)));

        bookmark.addTagBookmark(tagBookmark);
    }

    public void removeTagBookmark(final SessionUser user, final Long bookmarkId, final Long tagId) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
        final Bookmark bookmark = bookmarkService.findByIdAndUserId(bookmarkId, user.getId());
        final TagBookmark tagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)
                .orElseThrow(() -> new TagBookmarkNotFoundException(
                        "태그에 추가된 북마크를 찾을 수 없습니다. tagId : " + tag + " bookmarkId: " + bookmarkId));

        tagBookmarkRepository.delete(tagBookmark);
        bookmark.removeTagBookmark(tagBookmark);
    }
}
