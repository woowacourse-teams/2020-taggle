package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class TagServiceTest {
    private static final String TAG_NAME = "spring boot";

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagRepository tagRepository;

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

    @DisplayName("createTag: 중복된 태그가 존재하는 경우 이미 존재하는 태그를 반환한다.")
    @Test
    void createTag_TagAlreadyExist_ReturnExistTag() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest(TAG_NAME);

        // when
        tagService.createTag(user, tagCreateRequest);
        final TagResponse tagResponse = tagService.createTag(user, tagCreateRequest);

        // then
        assertThat(tagResponse.getId()).isNotNull();
        assertThat(tagResponse.getName()).isEqualTo(TAG_NAME);
    }

    @DisplayName("findTagById: 특정 태그를 조회한다.")
    @Test
    void findTagById() {
        // given
        final TagCreateRequest tagCreateRequest = new TagCreateRequest(TAG_NAME);
        final TagResponse tag = tagService.createTag(user, tagCreateRequest);

        // when
        final TagBookmarkResponse tagBookmarkResponse = tagService.findTagById(user, tag.getId());

        // then
        assertThat(tagBookmarkResponse.getBookmarks()).hasSize(0);
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
