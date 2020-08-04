package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.dto.CategoryDetailResponse;
import com.woowacourse.taggle.tag.dto.CategoryRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

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
        // given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        categoryService.createCategory(categoryRequest);

        // when
        List<CategoryDetailResponse> categories = categoryService.findCategories();

        // than
        assertThat(categories.size()).isEqualTo(1);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @DisplayName("updateCategory: 카테고리 타이틀을 변경한다.")
    @Test
    void updateCategory() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        final CategoryRequest changeRequest = new CategoryRequest("taggle");

        //when
        categoryService.updateCategory(categoryResponse.getId(), changeRequest);
        Category category = categoryRepository.findById(categoryResponse.getId()).get();

        //then
        assertThat(category.getTitle()).isEqualTo("taggle");
    }

    @DisplayName("removeCategory: 해당 카테고리를 삭제한다.")
    @Test
    void removeCategory() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);

        //when
        categoryService.removeCategory(categoryResponse.getId());

        //then
        assertThat(categoryRepository.existsById(categoryResponse.getId())).isFalse();
    }
}