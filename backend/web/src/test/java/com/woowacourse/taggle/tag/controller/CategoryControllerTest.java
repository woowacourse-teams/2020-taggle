package com.woowacourse.taggle.tag.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.CategorySetup;
import com.woowacourse.taggle.tag.controller.docs.CategoryDocumentation;
import com.woowacourse.taggle.tag.domain.Category;

public class CategoryControllerTest extends ControllerTest {

    @Autowired
    private CategorySetup categorySetup;

    @WithMockUser(value = "ADMIN")
    @DisplayName("createCategory: 카테고리를 추가한다.")
    @Test
    void createCategory() throws Exception {
        createByJsonParams("/api/v1/categories", "{ \"title\": \"project\" }")
                .andDo(CategoryDocumentation.createCategory());
    }

    // TODO User 적용시 리펙토링 필요
    // @WithMockUser(value = "ADMIN")
    // @DisplayName("findCategories: 카테고리 목록을 가져온다.")
    // @Test
    // void findCategories() throws Exception {
    //     categorySetup.saveWithTags();
    //
    //     read("/api/v1/categories/tags", jsonPath("$", hasSize(2)))
    //             .andDo(CategoryDocumentation.findCategories());
    // }

    @WithMockUser(value = "ADMIN")
    @DisplayName("updateCategory: 카테고리의 제목을 변경한다.")
    @Test
    void updateCategory() throws Exception {
        final Category category = categorySetup.saveWithTags();

        updateByJsonParams("/api/v1/categories/{id}", "{ \"title\": \"newCategory\" }", category.getId())
                .andDo(CategoryDocumentation.updateCategory());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeCategory: 카테고리 하나를 제거한다.")
    @Test
    void removeCategory() throws Exception {
        final Category category = categorySetup.save();

        remove("/api/v1/categories/{id}", category.getId())
                .andDo(CategoryDocumentation.removeCategory());
    }
}
