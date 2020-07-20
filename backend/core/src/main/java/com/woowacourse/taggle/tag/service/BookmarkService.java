package com.woowacourse.taggle.tag.service;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkCreateResponse;
import com.woowacourse.taggle.tag.dto.BookmarkRequest;
import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkCreateResponse addBookmark(final BookmarkCreateRequest bookmarkCreateRequest) {
        Bookmark bookmark = bookmarkRepository.findByUrl(bookmarkCreateRequest.getUrl())
                .orElse(bookmarkRepository.save(bookmarkCreateRequest.toEntity()));

        return BookmarkCreateResponse.of(bookmark);
    }

    public void removeBookmark(final BookmarkRequest bookmarkRemoveRequest) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkRemoveRequest.getId())
                .orElseThrow(() -> new BookmarkNotFoundException("삭제하려는 북마크가 존재하지 않습니다."));
        bookmarkRepository.delete(bookmark);
    }
}
