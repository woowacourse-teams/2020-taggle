package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.setup.domain.UserFixture;
import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.dto.CategoryRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;
import com.woowacourse.taggle.tag.dto.CategoryTagsResponse;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.CategoryNotFoundException;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    private SessionUser user;

    @BeforeEach
    void setUp() {
        final User testUser = userService.save(UserFixture.DEFAULT_USER);
        user = new SessionUser(testUser);
    }

    @DisplayName("createCategory: 카테고리를 추가한다.")
    @Test
    void createCategory() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest("project");

        // when
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);

        // than
        assertThat(categoryResponse.getId()).isNotNull();
        assertThat(categoryResponse.getTitle()).isEqualTo("project");
    }

    @DisplayName("createCategory: 이미 같은 이름의 카테고리가 존재하는 경우, 기존의 카테고리를 반환한다.")
    @Test
    void createCategory_CategoryDuplicationException() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest("project");

        // when
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);
        final CategoryResponse categoryResponseWithSameName = categoryService.createCategory(user, categoryRequest);
        // then
        assertThat(categoryResponse.getId()).isEqualTo(categoryResponseWithSameName.getId());
        assertThat(categoryResponse.getTitle()).isEqualTo(categoryResponseWithSameName.getTitle());
    }

    @DisplayName("findAllTagsBy: 카테고리를 포함한 모든 태그를 가져온다.")
    @Test
    void findAllTagsBy() {
        // given
        final TagCreateRequest tagCreateRequest1 = new TagCreateRequest("java");
        final TagCreateRequest tagCreateRequest2 = new TagCreateRequest("spring");
        final TagCreateRequest tagCreateRequest3 = new TagCreateRequest("oauth2");
        final TagCreateRequest tagCreateRequest4 = new TagCreateRequest("security");

        final TagResponse tagResponse1 = tagService.createTag(user, tagCreateRequest1);
        tagService.createTag(user, tagCreateRequest2);
        tagService.createTag(user, tagCreateRequest3);
        tagService.createTag(user, tagCreateRequest4);

        final CategoryRequest categoryRequest1 = new CategoryRequest("project1");
        final CategoryRequest categoryRequest2 = new CategoryRequest("project2");
        final CategoryResponse categoryResponse1 = categoryService.createCategory(user, categoryRequest1);
        categoryService.createCategory(user, categoryRequest2);

        categoryService.updateCategoryOnTag(user, categoryResponse1.getId(), tagResponse1.getId());

        // when
        final List<CategoryTagsResponse> categoryTagsResponses = categoryService.findAllTagsBy(user);

        // then
        assertThat(categoryTagsResponses).hasSize(3);
        assertThat(categoryTagsResponses.get(0).getId()).isNull();
        assertThat(categoryTagsResponses.get(1).getId()).isNotNull();
        assertThat(categoryTagsResponses.get(2).getId()).isNotNull();
    }

    @DisplayName("updateCategory: 카테고리 타이틀을 변경한다.")
    @Test
    void updateCategory() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);

        //when
        final CategoryRequest changeRequest = new CategoryRequest("taggle");
        categoryService.updateCategory(user, categoryResponse.getId(), changeRequest);
        final Category category = categoryService.findByIdAndUserId(categoryResponse.getId(), user.getId());

        //then
        assertThat(category.getTitle()).isEqualTo("taggle");
    }

    @DisplayName("updateCategoryOnTag: 태그의 카테고리를 변경한다.")
    @Test
    void updateCategoryOnTag() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);

        final TagCreateRequest tagCreateRequest = new TagCreateRequest("tag");
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);

        //when
        categoryService.updateCategoryOnTag(user, categoryResponse.getId(), tagResponse.getId());
        final Tag tag = tagService.findByIdAndUserId(tagResponse.getId(), user.getId());

        //then
        assertThat(tag.getCategory().getTitle()).isEqualTo("project");
    }

    @DisplayName("removeCategory: 해당 카테고리를 삭제한다.")
    @Test
    void removeCategory() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);

        //when
        categoryService.removeCategory(user, categoryResponse.getId());

        //then
        assertThat(categoryRepository.existsById(categoryResponse.getId())).isFalse();
    }

    @DisplayName("removeCategory: 카테고리가 존재하지 않는 경우, 예외가 발생한다.")
    @Test
    void removeCategory_NotFoundException() {
        // given
        // when
        // then
        assertThatThrownBy(() -> categoryService.removeCategory(user, 10L))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("카테고리가 존재하지 않습니다");
    }

    @DisplayName("removeCategory: 해당 카테고리를 삭제시, 하위 태그들의 카테고리가 Null 된다.")
    @Test
    void removeCategory_initCategoryOfTag() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);
        final TagCreateRequest tagCreateRequest = new TagCreateRequest("taggle");
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);
        categoryService.updateCategoryOnTag(user, categoryResponse.getId(), tagResponse.getId());

        //when
        categoryService.removeCategory(user, categoryResponse.getId());
        final Tag tag = tagService.findByIdAndUserId(tagResponse.getId(), user.getId());

        //then
        assertThat(tag.getCategory()).isNull();
    }
}