package com.woowacourse.taggle.tag.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.crawler.OpenGraphCrawler;
import com.woowacourse.taggle.dto.OpenGraph;
import com.woowacourse.taggle.tag.dto.BookmarkCreateDto;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkCreateService {

    private final OpenGraphCrawler openGraphCrawler;
    private final BookmarkService bookmarkService;

    public BookmarkResponse createBookmark(final BookmarkCreateRequest bookmarkCreateRequest, final SessionUser user) {
        final OpenGraph openGraph = openGraphCrawler.extractPreview(bookmarkCreateRequest.getUrl());
        final BookmarkCreateDto bookmarkCreateDto = BookmarkCreateDto.of(bookmarkCreateRequest, openGraph.getTitle(),
                openGraph.getDescription(), openGraph.getImage());

        return bookmarkService.createBookmark(user, bookmarkCreateDto);
    }
}
