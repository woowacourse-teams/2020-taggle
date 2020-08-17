package com.woowacourse.taggle.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.fixture.UserFixture;
import com.woowacourse.taggle.user.domain.User;

class BookmarkTest {

    private static final String URL = "https://github.com/taggle";

    @DisplayName("constructor: url을 입력받아 북마크를 생성한다.")
    @Test
    void constructor() {
        User user = UserFixture.DEFAULT_USER;

        assertThat(new Bookmark(URL, user, "title", "description", "image")).isInstanceOf(Bookmark.class);
    }
}
