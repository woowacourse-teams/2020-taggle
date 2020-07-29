package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookmarkSetup {

    private final BookmarkRepository bookmarkRepository;

    public void save() {
        final Bookmark bookmark = new Bookmark("http://github.com");

        bookmarkRepository.save(bookmark);
    }
}
