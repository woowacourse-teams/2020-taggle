package com.woowacourse.taggle.bookmark.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.taggle.bookmark.exception.AlreadyExistTagException;

class TagsTest {
    @DisplayName("empty: 빈 tags 인스턴스 생성")
    @Test
    void empty() {
        Tags tags = Tags.empty();
        assertAll(
                () -> assertThat(tags).isInstanceOf(Tags.class),
                () -> assertThat(tags.getTags()).hasSize(0)
        );
    }

    @DisplayName("addTag: 새로운 태그 추가")
    @Test
    void addTag() {
        // given
        Tags tags = Tags.empty();
        Tag tag = new Tag("hello");

        // when
        tags.addTag(tag);

        // then
        assertThat(tags.getTags()).hasSize(1);
    }

    @DisplayName("addTag: 이미 존재하는 태그를 추가하려고 할 때 예외 발생")
    @Test
    void addTag_ExistTag_ExceptionThrown() {
        // given
        Tags tags = Tags.empty();
        Tag tag1 = new Tag("hello");
        Tag tag2 = new Tag("hello");

        // when
        tags.addTag(tag1);

        // then
        assertThatThrownBy(() -> tags.addTag(tag2))
                .isInstanceOf(AlreadyExistTagException.class)
                .hasMessageContaining("이미 존재하는 태그입니다");
    }

    @DisplayName("removeTag: 새로운 태그 추가")
    @Test
    void removeTag() {
        // given
        Tags tags = Tags.empty();
        Tag tag = new Tag("hello");
        tags.addTag(tag);

        // when
        tags.removeTag(tag);

        // then
        assertThat(tags.getTags()).hasSize(0);
    }
}
