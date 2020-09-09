package kr.taggle.category.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.taggle.ControllerTest;
import kr.taggle.category.docs.CategoryDocumentation;
import kr.taggle.category.domain.Category;
import kr.taggle.setup.domain.CategorySetup;
import kr.taggle.setup.domain.TagSetup;
import kr.taggle.setup.domain.UserSetup;
import kr.taggle.tag.domain.Tag;
import kr.taggle.user.domain.User;

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

    @DisplayName("createCategory: 카테고리를 추가한다.")
    @Test
    void createCategory() throws Exception {
        categorySetup.save(user);
        createByJsonParams(user, "/api/v1/categories", "{\"title\":\"createCategory\"}")
                .andDo(CategoryDocumentation.createCategory());
    }

    @DisplayName("findCategories: 카테고리 목록을 가져온다.")
    @Test
    void findCategories() throws Exception {
        categorySetup.saveWithTag(user);
        read(user, "/api/v1/categories", jsonPath("$", hasSize(3)))
                .andDo(CategoryDocumentation.findCategories());
    }

    @DisplayName("updateCategory: 카테고리의 제목을 변경한다.")
    @Test
    void updateCategory() throws Exception {
        final Category category = categorySetup.save(user);
        updateByJsonParams(user, "/api/v1/categories/" + category.getId(), "{ \"title\": \"newCategory\" }")
                .andDo(CategoryDocumentation.updateCategory());

    }

    @DisplayName("updateCategoryOnTag: 태그의 카테고리를 변경한다.")
    @Test
    void updateCategoryOnTag() throws Exception {
        final Category category = categorySetup.save(user);
        final Tag tag = tagSetup.save(user);
        updateByPathVariables(user, "/api/v1/categories/{categoryId}/tags/{tagId}", +category.getId(), tag.getId())
                .andDo(CategoryDocumentation.updateCategoryOnTag());

    }

    @DisplayName("removeCategory: 카테고리 하나를 제거한다.")
    @Test
    void removeCategory() throws Exception {
        final Category category = categorySetup.save(user);
        removeByPathVariables(user, "/api/v1/categories/{categoryId}", category.getId())
                .andDo(CategoryDocumentation.removeCategory());

    }
}

