package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.dto.BookmarkCreateDto;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.dto.BookmarkTagResponse;
import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;

    public BookmarkResponse createBookmark(final SessionUser sessionUser,
            final BookmarkCreateDto bookmarkCreateDto) {
        final User user = userService.findById(sessionUser.getId());

        final Bookmark bookmark = bookmarkRepository.findByUrlAndUserId(bookmarkCreateDto.getUrl(), user.getId())
                .orElse(bookmarkRepository.save(bookmarkCreateDto.toEntityWithUser(user)));

        return BookmarkResponse.of(bookmark);
    }

    @Transactional(readOnly = true)
    public BookmarkTagResponse findBookmark(final SessionUser user, final Long id) {
        final Bookmark bookmark = findByIdAndUserId(id, user.getId());

        return BookmarkTagResponse.of(bookmark);
    }

    @Transactional(readOnly = true)
    public List<BookmarkResponse> findBookmarks(final SessionUser user) {
        final List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(user.getId());

        return BookmarkResponse.asList(bookmarks);
    }

    public void removeBookmark(final SessionUser user, final Long id) {
        final Bookmark bookmark = findByIdAndUserId(id, user.getId());

        bookmarkRepository.delete(bookmark);
    }

    public Bookmark findByIdAndUserId(final Long bookmarkId, final Long userId) {
        return bookmarkRepository.findByIdAndUserId(bookmarkId, userId)
                .orElseThrow(() -> new BookmarkNotFoundException("북마크가 존재하지 않습니다.\n"
                        + "bookmarkId: " + bookmarkId));
    }
}
