package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.TagSetup;
import com.woowacourse.taggle.tag.controller.doc.TagDocumentation;

public class TagControllerTest extends ControllerTest {

    @Autowired
    private TagSetup tagSetup;

    @WithMockUser(value = "ADMIN")
    @DisplayName("createTag: 태그를 추가한다.")
    @Test
    void createTag() throws Exception {
        create("/api/v1/tags", "{\"name\": \"taggle\"}")
                .andDo(TagDocumentation.createTag());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findTags() throws Exception {
        tagSetup.save();

        read("/api/v1/tags", jsonPath("$", hasSize(1)))
                .andDo(TagDocumentation.findTags());
    }
}
