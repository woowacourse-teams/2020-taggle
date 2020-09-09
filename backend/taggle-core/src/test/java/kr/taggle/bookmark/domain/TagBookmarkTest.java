package kr.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.taggle.fixture.UserFixture;
import kr.taggle.tag.domain.Tag;
import kr.taggle.user.domain.User;

public class TagBookmarkTest {

    @DisplayName("constructor: TagBookmark를 생성한다.")
    @Test
    void constructor() {
        final User user = UserFixture.DEFAULT_USER;

        final Tag tag = new Tag("taggle", user);
        final Bookmark bookmark = new Bookmark("https://github.com/taggle", user, "title", "description", "image");

        assertThat(new TagBookmark(tag, bookmark)).isInstanceOf(TagBookmark.class);
    }
}
