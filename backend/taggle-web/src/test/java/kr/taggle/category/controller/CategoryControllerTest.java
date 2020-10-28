package kr.taggle.category.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.taggle.ControllerTest;
import kr.taggle.category.docs.CategoryDocumentation;
import kr.taggle.category.dto.CategoryResponse;
import kr.taggle.category.service.CategoryService;
import kr.taggle.tag.dto.CategoryDetailResponse;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;

class CategoryControllerTest extends ControllerTest {

    @MockBean
    CategoryService categoryService;

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

    @DisplayName("createCategory: 카테고리를 추가한다.")
    @Test
    void createCategory() throws Exception {
        CategoryResponse categoryResponse = new CategoryResponse(1L, "webSite");
        when(categoryService.createCategory(any(), any())).thenReturn(categoryResponse);
        createByJsonParams(user, "/api/v1/categories", "{\"title\":\"createCategory\"}",
                jsonPath("$.title", Is.is("webSite")))
                .andDo(CategoryDocumentation.createCategory());
    }

    @DisplayName("expectBadRequestWhenCreateCategory: 카테고리 추가 시 카테고리의 제목 길이가 25 이상이면 오류 메시지를 보낸다.")
    @Test
    void expectBadRequestWhenCreateCategory() throws Exception {
        expectBadRequestWhenPostRequest(user, "/api/v1/categories",
                "{\"title\":\"this is a test for category-title-length\"}",
                jsonPath("$.title", Is.is("카테고리는 25자보다 클 수 없습니다.")));
    }

    @DisplayName("findCategories: 카테고리 목록을 가져온다.")
    @Test
    void findCategories() throws Exception {
        List<TagResponse> tagResponse = Arrays.asList(new TagResponse(1L, "tagResponse"));
        List<CategoryDetailResponse> webSite = Arrays.asList(new CategoryDetailResponse(1L, "webSite", tagResponse));
        when(categoryService.findAllTagsBy(any())).thenReturn(webSite);
        read(user, "/api/v1/categories", jsonPath("$", hasSize(1)))
                .andDo(CategoryDocumentation.findCategories());
    }

    @DisplayName("updateCategory: 카테고리의 제목을 변경한다.")
    @Test
    void updateCategory() throws Exception {
        updateByJsonParamsAndPathVariables(user, "/api/v1/categories/{categoryId}", "{ \"title\": \"newCategory\" }",
                1L)
                .andDo(CategoryDocumentation.updateCategory());
    }

    @DisplayName("removeCategory: 카테고리 하나를 제거한다.")
    @Test
    void removeCategory() throws Exception {
        removeByPathVariables(user, "/api/v1/categories/{categoryId}", 1L)
                .andDo(CategoryDocumentation.removeCategory());
    }
}

