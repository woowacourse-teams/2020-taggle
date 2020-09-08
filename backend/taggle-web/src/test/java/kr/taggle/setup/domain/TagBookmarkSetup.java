package kr.taggle.setup.domain;

import org.springframework.stereotype.Component;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.bookmark.domain.TagBookmark;
import kr.taggle.bookmark.domain.TagBookmarkRepository;
import kr.taggle.tag.domain.Tag;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagBookmarkSetup {

    private final TagBookmarkRepository tagBookmarkRepository;

    public TagBookmark save(final Tag tag, final Bookmark bookmark) {
        final TagBookmark tagBookmark = tagBookmarkRepository.save(new TagBookmark(tag, bookmark));

        bookmark.addTagBookmark(tagBookmark);

        return tagBookmark;
    }
}
