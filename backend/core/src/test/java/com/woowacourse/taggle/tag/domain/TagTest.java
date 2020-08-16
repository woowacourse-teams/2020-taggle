package com.woowacourse.taggle.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.setup.domain.UserFixture;
import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;

class TagTest {

    @DisplayName("constructor: 태그를 생성한다.")
    @Test
    void constructor() {
        User user = UserFixture.DEFAULT_USER;

        assertThat(new Tag("taggle", user)).isInstanceOf(Tag.class);
    }
}
