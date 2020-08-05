package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.CategorySetup;
import com.woowacourse.taggle.tag.controller.docs.BookmarkDocumentation;
import com.woowacourse.taggle.tag.controller.docs.CategoryDocumentation;

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

    @WithMockUser(value = "ADMIN")
    @DisplayName("findCategories: 카테고리 목록을 가져온다.")
    @Test
    void findCategories() throws Exception {
        categorySetup.save();

        read("/api/v1/categories", jsonPath("$", hasSize(1)))
                .andDo(CategoryDocumentation.findCategories());
    }
}
