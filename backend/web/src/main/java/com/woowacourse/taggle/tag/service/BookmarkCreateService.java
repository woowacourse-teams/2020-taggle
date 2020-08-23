package com.woowacourse.taggle.tag.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.linkpreview.LinkPreview;
import com.woowacourse.taggle.linkpreview.LinkPreviewCrawler;
import com.woowacourse.taggle.tag.dto.BookmarkCreateDto;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.user.dto.SessionUser;
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
