package com.woowacourse.taggle.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategoryTest extends DomainCommonUser {

    @DisplayName("constructor: 카테고리를 생성한다.")
    @Test
    void constructor() {
        assertThat(new Category("project", user)).isInstanceOf(Category.class);
    }
}