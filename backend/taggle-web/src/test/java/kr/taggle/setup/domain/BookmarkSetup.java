package kr.taggle.setup.domain;

import org.springframework.stereotype.Component;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.bookmark.domain.BookmarkRepository;
import kr.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookmarkSetup {

    private final BookmarkRepository bookmarkRepository;

    public Bookmark save(final User user) {
        final Bookmark bookmark = new Bookmark("http://github.com", user, "title", "description", "image");

        return bookmarkRepository.save(bookmark);
    }
}
