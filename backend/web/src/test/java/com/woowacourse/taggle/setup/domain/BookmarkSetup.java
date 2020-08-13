package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.BookmarkRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookmarkSetup {

    private final BookmarkRepository bookmarkRepository;

    private final UserSetup userSetup;

    public Bookmark save() {

        User user = userSetup.save();
        final Bookmark bookmark = new Bookmark("http://github.com", user);

        return bookmarkRepository.save(bookmark);
    }

    public Bookmark saveBookmarkWithTag(Tag tag) {
        return bookmarkRepository.save(new Bookmark("http://github.com", tag.getUser()));
    }

    public void removeAll() {
        bookmarkRepository.deleteAll();
    }
}
