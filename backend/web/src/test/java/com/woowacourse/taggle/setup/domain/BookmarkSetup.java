package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookmarkSetup {

    private final BookmarkRepository bookmarkRepository;

    public Bookmark save(final User user) {
        final Bookmark bookmark = new Bookmark("http://github.com", user);

        return bookmarkRepository.save(bookmark);
    }
}
