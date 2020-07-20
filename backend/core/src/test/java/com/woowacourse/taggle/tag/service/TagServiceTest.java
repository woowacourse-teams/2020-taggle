package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class TagServiceTest {
    @Autowired
    private TagService tagService;

    @DisplayName("createTag: 태그를 생성한다.")
    @Test
    void createTag() {
        // given
        String tagName = "spring boot";
        TagCreateRequest tagCreateRequest = new TagCreateRequest(tagName);

        // when
        TagResponse tagResponse = tagService.createTag(tagCreateRequest);

        // then
        assertThat(tagResponse.getId()).isNotNull();
        assertThat(tagResponse.getName()).isEqualTo(tagName);
    }

    @DisplayName("createTag: 중복된 태그가 존재하는 경우 이미 존재하는 태그를 반환한다.")
    @Test
    void createTag_TagAlreadyExist_ReturnExistTag() {
        // given
        String tagName = "spring boot";
        TagCreateRequest tagCreateRequest = new TagCreateRequest(tagName);

        // when
        tagService.createTag(tagCreateRequest);
        TagResponse tagResponse = tagService.createTag(tagCreateRequest);

        // then
        assertThat(tagResponse.getId()).isNotNull();
        assertThat(tagResponse.getName()).isEqualTo(tagName);
    }

    @DisplayName("removeTag: 태그를 제거한다.")
    @Test
    void removeTag() {
        // given
        String tagName = "spring boot";
        TagCreateRequest tagCreateRequest = new TagCreateRequest(tagName);
        tagService.createTag(tagCreateRequest);
        TagResponse tagResponse = tagService.createTag(tagCreateRequest);
        TagRequest tagRequest = new TagRequest(tagResponse.getId());

        // when
        tagService.removeTag(tagRequest);

        // then
        assertThatThrownBy(() -> tagService.findTag(new TagRequest(tagResponse.getId())))
                .isInstanceOf(TagNotFoundException.class)
                .hasMessageContaining("태그가 존재하지 않습니다");
    }

    @DisplayName("removeTag: 태그가 존재하지 않으면 예외가 발생한다.")
    @Test
    void removeTag_TagNotExist_ExceptionThrown() {
        // given
        TagRequest tagRequest = new TagRequest(1L);

        // when
        // then
        assertThatThrownBy(() -> tagService.removeTag(tagRequest))
                .isInstanceOf(TagNotFoundException.class)
                .hasMessageContaining("태그가 존재하지 않습니다");
    }
}
