package kr.taggle.bookmark.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.bookmark.domain.BookmarkRepository;
import kr.taggle.bookmark.domain.TagBookmark;
import kr.taggle.bookmark.domain.TagBookmarkRepository;
import kr.taggle.bookmark.dto.BookmarkCreateDto;
import kr.taggle.bookmark.dto.BookmarkPageRequest;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.bookmark.dto.BookmarkDetailResponse;
import kr.taggle.bookmark.dto.TagDetailResponse;
import kr.taggle.bookmark.exception.BookmarkNotFoundException;
import kr.taggle.tag.domain.Tag;
import kr.taggle.tag.service.TagService;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkService {

    private final UserService userService;
    private final TagService tagService;
    private final BookmarkRepository bookmarkRepository;
    private final TagBookmarkRepository tagBookmarkRepository;

    public BookmarkResponse createBookmark(final SessionUser sessionUser, final BookmarkCreateDto bookmarkCreateDto) {
        final User user = userService.findById(sessionUser.getId());
        final Bookmark bookmark = bookmarkRepository.findByUrlAndUserId(bookmarkCreateDto.getUrl(), user.getId())
                .orElseGet(() -> bookmarkRepository.save(bookmarkCreateDto.toEntityWithUser(user)));

        return BookmarkResponse.of(bookmark);
    }

    @Transactional(readOnly = true)
    public List<BookmarkResponse> findBookmarks(final SessionUser user, final BookmarkPageRequest bookmarkPageRequest) {
        final Page<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(user.getId(),
                bookmarkPageRequest.toPageable());

        return BookmarkResponse.asList(bookmarks.getContent());
    }

    @Transactional(readOnly = true)
    public TagDetailResponse findBookmarksByTagId(final SessionUser user, final Long tagId,
            final BookmarkPageRequest bookmarkPageRequest) {
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());
        final Page<TagBookmark> bookmarks = tagBookmarkRepository.findAllByTag(tag, bookmarkPageRequest.toPageable());

        return TagDetailResponse.of(tag, bookmarks.getContent());
    }

    @Transactional(readOnly = true)
    public TagDetailResponse findUntaggedBookmarks(final SessionUser user,
            final BookmarkPageRequest bookmarkPageRequest) {
        final Page<Bookmark> bookmarks = findUntaggedBookmarksByUserId(user.getId(),
                bookmarkPageRequest.toPageable());
        return TagDetailResponse.ofUntagged(bookmarks.getContent());
    }

    @Transactional(readOnly = true)
    public BookmarkDetailResponse findTagsByBookmarkId(final SessionUser user, final Long id) {
        final Bookmark bookmark = findByIdAndUserId(id, user.getId());

        return BookmarkDetailResponse.of(bookmark);
    }

    public void removeBookmark(final SessionUser user, final Long id) {
        final Bookmark bookmark = findByIdAndUserId(id, user.getId());

        bookmarkRepository.delete(bookmark);
    }

    public Page<Bookmark> findUntaggedBookmarksByUserId(final Long userId, final Pageable pageable) {
        return bookmarkRepository.findAllByUserIdAndTagsEmpty(userId, pageable);
    }

    public Bookmark findByIdAndUserId(final Long bookmarkId, final Long userId) {
        return bookmarkRepository.findByIdAndUserId(bookmarkId, userId)
                .orElseThrow(() -> new BookmarkNotFoundException("북마크가 존재하지 않습니다. bookmarkId: " + bookmarkId));
    }
}
