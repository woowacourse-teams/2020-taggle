package com.woowacourse.taggle.tag.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.tag.controller.docs.CategoryDocumentation;

public class CategoryControllerTest extends ControllerTest {

    @WithMockUser(value = "ADMIN")
    @DisplayName("createCategory: 카테고리를 추가한다.")
    @Test
    void createCategory() throws Exception {
        createByJsonParams("/api/v1/categories", "{ \"title\": \"project\" }")
                .andDo(CategoryDocumentation.createCategory());
    }
}
