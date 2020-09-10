package kr.taggle.tag.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.taggle.ControllerTest;
import kr.taggle.category.domain.Category;
import kr.taggle.setup.domain.CategorySetup;
import kr.taggle.setup.domain.TagSetup;
import kr.taggle.setup.domain.UserSetup;
import kr.taggle.tag.docs.TagDocumentation;
import kr.taggle.tag.domain.Tag;
import kr.taggle.user.domain.User;

public class TagControllerTest extends ControllerTest {

    @Autowired
    private TagSetup tagSetup;

    @Autowired
    private CategorySetup categorySetup;

    @Autowired
    private UserSetup userSetup;

    private User user;

    @BeforeEach
    void setUp() {
        user = userSetup.save();
    }

    @DisplayName("createTag: 태그를 추가한다.")
    @Test
    void createTag() throws Exception {
        createByJsonParams(user, "/api/v1/tags", "{\"name\": \"taggle\"}")
                .andDo(TagDocumentation.createTag());
    }

    @DisplayName("removeTag: 태그를 삭제한다.")
    @Test
    void removeTag() throws Exception {
        final Tag tag = tagSetup.save(user);

        removeByPathVariables(user, "/api/v1/tags/{tagId}", tag.getId())
                .andDo(TagDocumentation.removeTag());
    }

    @DisplayName("updateTag: 태그의 카테고리를 변경한다.")
    @Test
    void updateTag() throws Exception {
        final Category category = categorySetup.save(user);
        final Tag tag = tagSetup.save(user);
        updateByJsonParamsAndPathVariables(user, "/api/v1/tags/{tagId}", String.format( "{\"categoryId\" : %d }", category.getId()), tag.getId())
                .andDo(TagDocumentation.updateTag());

    }
}
