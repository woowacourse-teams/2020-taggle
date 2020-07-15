package com.woowacourse.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.usertag.UserTag;

public class UserTagTest {

    @DisplayName("constructor: 유저 태그 생성")
    @Test
    void constructor() {
        assertThat(new UserTag()).isInstanceOf(UserTag.class);
    }
}
