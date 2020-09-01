package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import com.woowacourse.taggle.tag.domain.TagBookmarkRepository;
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
