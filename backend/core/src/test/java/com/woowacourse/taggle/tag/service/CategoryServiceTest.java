package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.tag.dto.CategoryRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @DisplayName("createCategory: 카테고리를 추가한다.")
    @Test
    void createCategory() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest("project");

        // when
        final CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);

        // than
        assertThat(categoryResponse.getId()).isNotNull();
        assertThat(categoryResponse.getTitle()).isEqualTo("project");
    }

    @DisplayName("findCategories: 카테고리 목록을 가져온다.")
    @Test
    void findCategories() {
    }

    @DisplayName("displayName")
    @Test
    void updateCategory() {
    }

    @DisplayName("displayName")
    @Test
    void removeCategory() {
    }
}