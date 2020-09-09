package kr.taggle.bookmark.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.taggle.bookmark.dto.BookmarkCreateDto;
import kr.taggle.bookmark.dto.BookmarkCreateRequest;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.linkpreview.LinkPreview;
import kr.taggle.linkpreview.LinkPreviewCrawler;
import kr.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkCreateService {

    private final LinkPreviewCrawler linkPreviewCrawler;
    private final BookmarkService bookmarkService;

    public BookmarkResponse createBookmark(final BookmarkCreateRequest bookmarkCreateRequest, final SessionUser user) {
        final LinkPreview linkPreview = linkPreviewCrawler.extractPreview(bookmarkCreateRequest.getUrl());
        final BookmarkCreateDto bookmarkCreateDto = BookmarkCreateDto.of(bookmarkCreateRequest, linkPreview.getTitle(),
                linkPreview.getDescription(), linkPreview.getImage());

        return bookmarkService.createBookmark(user, bookmarkCreateDto);
    }
}
