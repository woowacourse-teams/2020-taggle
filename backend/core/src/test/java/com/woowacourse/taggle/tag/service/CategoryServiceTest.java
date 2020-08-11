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
import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.CategoryDetailResponse;
import com.woowacourse.taggle.tag.dto.CategoryRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.CategoryDuplicationException;
import com.woowacourse.taggle.tag.exception.CategoryNotFoundException;
import com.woowacourse.taggle.user.domain.Role;
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
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    private SessionUser user;

    @BeforeEach
    void setUp() {
        final User testUser = userService.save(User.builder()
                .email("a@a.com")
                .nickName("tigger")
                .role(Role.USER)
                .picture("https://www.naver.com/")
                .build());
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

    @DisplayName("createCategory: 중복된 카테고리가 존재하는 경우 예외가 발생한다.")
    @Test
    void createCategory_CategoryDuplicationException() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        categoryService.createCategory(user, categoryRequest);

        // when
        // then
        assertThatThrownBy(() -> categoryService.createCategory(user, categoryRequest))
                .isInstanceOf(CategoryDuplicationException.class)
                .hasMessageContaining("이미 존재하는 카테고리입니다.");
    }

    @DisplayName("findCategories: 카테고리 목록을 가져온다.")
    @Test
    void findCategories() {
        // given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        categoryService.createCategory(user, categoryRequest);

        // when
        final List<CategoryDetailResponse> categories = categoryService.findCategories(user);

        // than
        assertThat(categories.size()).isEqualTo(2);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @DisplayName("updateCategory: 카테고리 타이틀을 변경한다.")
    @Test
    void updateCategory() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);
        System.out.println(categoryResponse.getTitle() + "@@@@@@@@@@@@");
        //when
        final CategoryRequest changeRequest = new CategoryRequest("taggle");
        categoryService.updateCategory(user, categoryResponse.getId(), changeRequest);
        final Category category = categoryRepository.findById(categoryResponse.getId()).get();

        //then
        assertThat(category.getTitle()).isEqualTo("taggle");
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

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @DisplayName("removeCategory: 해당 카테고리를 삭제시, 하위 태그들의 카테고리가 초기화된다.")
    @Test
    void removeCategory_initCategoryOfTag() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);
        final TagCreateRequest tagCreateRequest = new TagCreateRequest("taggle");
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);
        tagService.updateCategory(user, tagResponse.getId(), categoryResponse.getId());

        //when
        categoryService.removeCategory(user, categoryResponse.getId());
        final Tag tag = tagRepository.findById(tagResponse.getId()).get();

        //then
        System.out.println(tag.getCategory().getTitle());
        assertThat(tag.getCategory().getTitle()).isEqualTo("Uncategorized");
    }
}