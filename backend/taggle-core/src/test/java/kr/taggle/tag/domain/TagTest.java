package kr.taggle.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.taggle.category.domain.Category;
import kr.taggle.fixture.UserFixture;
import kr.taggle.user.domain.User;

class TagTest {
    private static final User USER = UserFixture.DEFAULT_USER;

    @DisplayName("updateCategory: 카테고리를 변경한다.")
    @Test
    void updateCategory() {
        // given
        final Category category = Category.builder()
                .title("category")
                .build();
        final Tag tag = Tag.builder()
                .name("tag")
                .category(category)
                .user(USER)
                .build();

        // when
        tag.updateCategory(category);

        // then
        assertThat(tag.getCategory()).isEqualTo(category);
    }
}
