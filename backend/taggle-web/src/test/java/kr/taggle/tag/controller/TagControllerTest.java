package kr.taggle.tag.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.taggle.ControllerTest;
import kr.taggle.tag.docs.TagDocumentation;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.tag.service.TagService;
import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;

class TagControllerTest extends ControllerTest {

    @MockBean
    TagService tagService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .nickName("User")
                .email("User@gmail.com")
                .notificationEmail("notiUser@gmail.com")
                .phoneNumber(null)
                .picture("picture")
                .notificationEnabled(Boolean.FALSE)
                .role(Role.USER)
                .build();
    }

    @DisplayName("createTag: 태그를 추가한다.")
    @Test
    void createTag() throws Exception {
        TagResponse tagResponse = new TagResponse(1L, "taggle");
        when(tagService.createTag(any(),any())).thenReturn(tagResponse);
        createByJsonParams(user, "/api/v1/tags", "{\"name\": \"taggle\"}",
                jsonPath("$.name", Is.is("taggle")))
                .andDo(TagDocumentation.createTag());
    }

    @DisplayName("createTag: 태그 추가 시 태그의 길이가 25 이상이면 오류 메시지를 보낸다.")
    @Test
    void createTag_ExceedMaxTagName_BadRequest() throws Exception {
        expectBadRequestWhenPostRequest(user, "/api/v1/tags", "{\"name\":\"this is a test for tag-name-length\"}",
                jsonPath("$.name", Is
                        .is("태그는 25자보다 클 수 없습니다.")));
    }

    @DisplayName("removeTag: 태그를 삭제한다.")
    @Test
    void removeTag() throws Exception {

        removeByPathVariables(user, "/api/v1/tags/{tagId}", 1L)
                .andDo(TagDocumentation.removeTag());
    }

    @DisplayName("updateTag: 태그의 카테고리를 변경한다.")
    @Test
    void updateTag() throws Exception {
        updateByJsonParamsAndPathVariables(user, "/api/v1/tags/{tagId}",
                String.format("{\"categoryId\" : %d }", 1L),1L)
                .andDo(TagDocumentation.updateTag());
    }
}
