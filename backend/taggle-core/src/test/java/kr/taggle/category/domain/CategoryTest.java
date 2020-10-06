package kr.taggle.category.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.taggle.fixture.UserFixture;
import kr.taggle.user.domain.User;

public class CategoryTest {

    @DisplayName("constructor: 카테고리를 생성한다.")
    @Test
    void constructor() {
        final User user = UserFixture.DEFAULT_USER;

        assertThat(new Category("project", user)).isInstanceOf(Category.class);
    }
}
