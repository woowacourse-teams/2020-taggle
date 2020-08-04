package com.woowacourse.taggle.tag.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
class CategoryServiceTest {

    @DisplayName("displayName")
    @Test
    void createCategory() {

    }

    @DisplayName("displayName")
    @Test
    void findCategories() {
    }

    @DisplayName("displayName")
    @Test
    void updateCategory() {
    }

    @DisplayName("displayName")
    @Test
    void removeCategory() {
    }
}