package kr.taggle.tag.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.taggle.ControllerTest;
import kr.taggle.setup.domain.TagSetup;
import kr.taggle.setup.domain.UserSetup;
import kr.taggle.tag.docs.TagDocumentation;
import kr.taggle.tag.domain.Tag;
import kr.taggle.user.domain.User;

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

    @DisplayName("createTag: 태그를 추가한다.")
    @Test
    void createTag() throws Exception {
        createByJsonParams(user, "/api/v1/tags", "{\"name\": \"taggle\"}")
                .andDo(TagDocumentation.createTag());
    }

    @DisplayName("validateWhenTagCreateRequest: 테그 추가 시 태그의 길이가 25 이상이면 오류 메시지를 보낸다.")
    @Test
    void validateWhenTagCreateRequest() throws Exception {
        validateWhenPostRequest(user, "/api/v1/tags", "{\"name\":\"this is a test for tag-name-length\"}", jsonPath("$.name", Is
                .is("태그는 25자보다 클 수 없습니다.")));
    }

    @DisplayName("removeTag: 태그를 삭제한다.")
    @Test
    void removeTag() throws Exception {
        final Tag tag = tagSetup.save(user);

        removeByPathVariables(user, "/api/v1/tags/{tagId}", tag.getId())
                .andDo(TagDocumentation.removeTags());
    }
}
