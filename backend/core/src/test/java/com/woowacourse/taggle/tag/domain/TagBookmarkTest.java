package com.woowacourse.taggle.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TagBookmarkTest extends DomainCommonUser {

    @DisplayName("constructor: TagBookmark를 생성한다.")
    @Test
    void constructor() {
        final Tag tag = new Tag("taggle", user);
        final Bookmark bookmark = new Bookmark("https://github.com/taggle", user, "title", "description", "image");

        assertThat(new TagBookmark(tag, bookmark)).isInstanceOf(TagBookmark.class);
    }
}
