package kr.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taggle.JpaTestConfiguration;
import kr.taggle.category.dto.CategoryRequest;
import kr.taggle.category.dto.CategoryResponse;
import kr.taggle.category.service.CategoryService;
import kr.taggle.fixture.UserFixture;
import kr.taggle.tag.domain.Tag;
import kr.taggle.tag.domain.TagRepository;
import kr.taggle.tag.dto.TagCreateRequest;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.tag.dto.TagUpdateRequest;
import kr.taggle.tag.exception.TagNotFoundException;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class TagServiceTest {
    private static final String TAG_NAME = "spring boot";

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagRepository tagRepository;

    private SessionUser user;

    @BeforeEach
    void setUp() {
        final User testUser = userService.save(UserFixture.DEFAULT_USER);
        user = new SessionUser(testUser);
    }

    @DisplayName("createTag: 태그를 생성한다.")
    @Test
    void createTag() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest(TAG_NAME);

        // when
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);

        // then
        assertThat(tagResponse.getId()).isNotNull();
        assertThat(tagResponse.getName()).isEqualTo(TAG_NAME);
    }

    @DisplayName("createTag: 이미 같은 이름의 태그가 존재하는 경우, 기존의 태그를 반환한다.")
    @Test
    void createTag_TagAlreadyExist_ReturnExistTag() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest(TAG_NAME);

        // when
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);
        final TagResponse tagResponseWithSameName = tagService.createTag(user, tagCreateRequest);

        // then
        assertThat(tagResponse.getId()).isEqualTo(tagResponseWithSameName.getId());
        assertThat(tagResponse.getName()).isEqualTo(tagResponseWithSameName.getName());
    }

    @DisplayName("updateCategoryOnTag: 태그의 카테고리를 변경한다.")
    @Test
    void updateCategoryOnTag() {
        //given
        final CategoryRequest categoryRequest = new CategoryRequest("project");
        final CategoryResponse categoryResponse = categoryService.createCategory(user, categoryRequest);
        final TagUpdateRequest tagUpdateRequest = new TagUpdateRequest(categoryResponse.getId());

        final TagCreateRequest tagCreateRequest = new TagCreateRequest("tag");
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);

        //when
        tagService.updateTag(user, tagUpdateRequest, tagResponse.getId());
        final Tag tag = tagService.findByIdAndUserId(tagResponse.getId(), user.getId());

        //then
        assertThat(tag.getCategory().getTitle()).isEqualTo("project");
    }

    @DisplayName("removeTag: 태그를 제거한다.")
    @Test
    void removeTag() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest(TAG_NAME);
        tagService.createTag(user, tagCreateRequest);
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);

        // when
        tagService.removeTag(user, tagResponse.getId());

        // then
        assertThat(tagRepository.existsById(tagResponse.getId())).isFalse();
    }

    @DisplayName("removeTag: 태그를 제거한다.")
    @Test
    void removeTag_TagBookmarkExist() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest(TAG_NAME);
        tagService.createTag(user, tagCreateRequest);
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);

        // when
        tagService.removeTag(user, tagResponse.getId());

        // then
        assertThat(tagRepository.existsById(tagResponse.getId())).isFalse();
    }

    @DisplayName("removeTag: 태그가 존재하지 않으면 예외가 발생한다.")
    @Test
    void removeTag_TagNotExist_ExceptionThrown() {
        // given
        // when
        // then
        assertThatThrownBy(() -> tagService.removeTag(user, 2L))
                .isInstanceOf(TagNotFoundException.class)
                .hasMessageContaining("태그가 존재하지 않습니다");
    }

}
