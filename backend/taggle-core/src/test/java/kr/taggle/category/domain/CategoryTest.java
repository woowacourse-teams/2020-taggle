package kr.taggle.category.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.taggle.fixture.UserFixture;
import kr.taggle.user.domain.User;

class CategoryTest {
    private static final User USER = UserFixture.DEFAULT_USER;

    @DisplayName("update: 카테고리를 수정한다.")
    @Test
    void update() {
        // given
        final Category category = Category.builder()
                .title("category")
                .user(USER)
                .build();
        final Category updateCategory = Category.builder()
                .title("update")
                .build();

        // when
        category.update(updateCategory);

        // then
        assertThat(category.getTitle()).isEqualTo("update");
    }

    @DisplayName("update: 변경할 내역이 없으면 변경하지 않는다.")
    @Test
    void update_NotUpdated() {
        // given
        final Category category = Category.builder()
                .title("category")
                .user(USER)
                .build();
        final Category updateCategory = Category.builder()
                .build();

        // when
        category.update(updateCategory);

        // then
        assertThat(category.getTitle()).isEqualTo("category");
    }
}
