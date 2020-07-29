package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkResponse createBookmark(final BookmarkCreateRequest bookmarkCreateRequest) {
        final Bookmark bookmark = bookmarkRepository.findByUrl(bookmarkCreateRequest.getUrl())
                .orElse(bookmarkRepository.save(bookmarkCreateRequest.toEntity()));

        return BookmarkResponse.of(bookmark);
    }

    public void removeBookmark(final BookmarkRequest bookmarkRemoveRequest) {
        final Bookmark bookmark = bookmarkRepository.findById(bookmarkRemoveRequest.getId())
                .orElseThrow(() -> new BookmarkNotFoundException("삭제하려는 북마크가 존재하지 않습니다.\n"
                        + "bookmarkId: " + bookmarkRemoveRequest.getId()));
        bookmarkRepository.delete(bookmark);
    }

    public List<BookmarkResponse> findBookmarks() {
        final List<Bookmark> bookmarks = bookmarkRepository.findAll();

        return BookmarkResponse.asList(bookmarks);
    }
}
