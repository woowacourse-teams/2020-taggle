package com.woowacourse.taggle.tag.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.crawler.OpenGraphCrawler;
import com.woowacourse.taggle.dto.OpenGraphDto;
import com.woowacourse.taggle.tag.dto.BookmarkCreateDto;
import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BookmarkCrawlerService {

    private final OpenGraphCrawler openGraphCrawler;

    public BookmarkCreateDto findOpenGraph(BookmarkCreateRequest bookmarkCreateRequest) {
        OpenGraphDto openGraph = openGraphCrawler.findOpenGraph(bookmarkCreateRequest.getUrl());

        return BookmarkCreateDto.of(bookmarkCreateRequest, openGraph.getTitle(), openGraph.getDescription(),
                openGraph.getImage());
    }
}
