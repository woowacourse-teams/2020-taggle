package com.woowacourse.taggle.tag.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.TagSetup;
import com.woowacourse.taggle.setup.domain.UserSetup;
import com.woowacourse.taggle.tag.controller.docs.TagDocumentation;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.user.domain.User;

public class TagControllerTest extends ControllerTest {

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
    @DisplayName("createTag: 태그를 추가한다.")
    @Test
    void createTag() throws Exception {
        createByJsonParams(user, "/api/v1/tags", "{\"name\": \"taggle\"}")
                .andDo(TagDocumentation.createTag());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeTag: 태그를 삭제한다.")
    @Test
    void removeTag() throws Exception {
        final Tag tag = tagSetup.save(user);

        removeByPathVariables(user, "/api/v1/tags/{tagId}", tag.getId())
                .andDo(TagDocumentation.removeTags());
    }
}
