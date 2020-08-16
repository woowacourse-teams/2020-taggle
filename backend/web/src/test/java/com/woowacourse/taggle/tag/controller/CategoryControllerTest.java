package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.CategorySetup;
import com.woowacourse.taggle.setup.domain.TagSetup;
import com.woowacourse.taggle.setup.domain.UserSetup;
import com.woowacourse.taggle.tag.controller.docs.CategoryDocumentation;
import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.user.domain.User;

public class CategoryControllerTest extends ControllerTest {

    @Autowired
    private CategorySetup categorySetup;

    @Autowired
    private TagSetup tagSetup;

    @Autowired
    private UserSetup userSetup;

    private User user;

    @BeforeEach
    void setUp() {
        user = userSetup.save();
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("createCategory: 카테고리를 추가한다.")
    @Test
    void createCategory() throws Exception {
        categorySetup.save(user);
        createByJsonParams(user, "/api/v1/categories", "{\"title\":\"createCategory\"}")
                .andDo(CategoryDocumentation.createCategory());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findCategories: 카테고리 목록을 가져온다.")
    @Test
    void findCategories() throws Exception {
        categorySetup.saveWithTag(user);
        read(user, "/api/v1/categories", jsonPath("$", hasSize(1)))
                .andDo(CategoryDocumentation.findCategories());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("updateCategory: 카테고리의 제목을 변경한다.")
    @Test
    void updateCategory() throws Exception {
        final Category category = categorySetup.save(user);
        updateByJsonParams(user, "/api/v1/categories/" + category.getId(), "{ \"title\": \"newCategory\" }")
                .andDo(CategoryDocumentation.updateCategory());

    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("updateCategoryOnTag: 태그의 카테고리를 변경한다.")
    @Test
    void updateCategoryOnTag() throws Exception {
        final Category category = categorySetup.save(user);
        final Tag tag = tagSetup.save(user);
        updateByPathVariables(user, "/api/v1/categories/{categoryId}/tags/{tagId}", +category.getId(), tag.getId())
                .andDo(CategoryDocumentation.updateCategoryOnTag());

    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeCategory: 카테고리 하나를 제거한다.")
    @Test
    void removeCategory() throws Exception {
        final Category category = categorySetup.save(user);
        removeByPathVariables(user, "/api/v1/categories/{categoryId}", category.getId())
                .andDo(CategoryDocumentation.removeCategory());

    }
}

