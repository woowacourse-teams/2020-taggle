package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.dto.BookmarkTagResponse;
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

    public void removeBookmark(final Long id) {
        final Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new BookmarkNotFoundException("삭제하려는 북마크가 존재하지 않습니다.\n"
                        + "bookmarkId: " + id));
        bookmarkRepository.delete(bookmark);
    }

    public List<BookmarkResponse> findBookmarks() {
        final List<Bookmark> bookmarks = bookmarkRepository.findAll();

        return BookmarkResponse.asList(bookmarks);
    }

    public BookmarkTagResponse findBookmark(final Long id) {
        final Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new BookmarkNotFoundException("북마크가 존재하지 않습니다. \n"
                        + "bookmarkId: " + id));

        return BookmarkTagResponse.of(bookmark);
    }
}
