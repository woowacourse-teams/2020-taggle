package com.woowacourse.taggle.category.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.fixture.UserFixture;
import com.woowacourse.taggle.user.domain.User;

class CategoryTest {

    @DisplayName("constructor: 카테고리를 생성한다.")
    @Test
    void constructor() {
        final User user = UserFixture.DEFAULT_USER;

        assertThat(new Category("project", user)).isInstanceOf(Category.class);
    }
}
