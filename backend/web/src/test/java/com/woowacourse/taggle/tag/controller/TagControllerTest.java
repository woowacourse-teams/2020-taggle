package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.TagSetup;
import com.woowacourse.taggle.tag.controller.docs.TagDocumentation;
import com.woowacourse.taggle.tag.domain.Tag;

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
    @DisplayName("findTags: 전체 태그를 조회한다.")
    @Test
    void findTags() throws Exception {
        tagSetup.save();

        read("/api/v1/tags", jsonPath("$", hasSize(1)))
                .andDo(TagDocumentation.findTags());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeTag: 태그를 삭제한다.")
    @Test
    void removeTag() throws Exception {
        final Tag tag = tagSetup.save();

        remove("/api/v1/tags/{id}", tag.getId())
                .andDo(TagDocumentation.removeTags());
    }
}
