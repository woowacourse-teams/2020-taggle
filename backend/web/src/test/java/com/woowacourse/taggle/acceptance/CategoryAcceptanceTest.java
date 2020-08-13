package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.setup.domain.TagSetup;
import com.woowacourse.taggle.tag.dto.CategoryTagsResponse;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @Autowired
    private TagSetup tagSetup;

    @Transactional
    @WithMockUser(roles = "ADMIN")
    @Test
    void manageCategory() {
        // 카테고리를 생성한다.
        createCategory("project");

        // 카테고리를 가져온다.
        // final Tag tag = tagSetup.save();
        List<CategoryTagsResponse> categories = findCategories();
        assertThat(categories).hasSize(2);

        // 카테고리를 수정한다.
        final Long categoryId = categories.get(1).getId();
        final String updateTitle = "service";
        updateCategory(categoryId, updateTitle);
        final List<CategoryTagsResponse> updateCategories = findCategories();
        assertThat(updateCategories.get(1).getTitle()).isEqualTo(updateTitle);

        // 카테고리를 제거한다.
        removeCategory(categoryId);

        categories = findCategories();
        assertThat(categories).hasSize(1);
    }

    public List<CategoryTagsResponse> findCategories() {
        return getAsList("/api/v1/categories", CategoryTagsResponse.class);
    }

    private void createCategory(final String title) {
        final Map<String, Object> request = new HashMap<>();
        request.put("id", 1L);
        request.put("title", title);

        post("/api/v1/categories", request, "/api/v1/categories");
    }

    private void updateCategory(final Long id, final String title) {
        final Map<String, Object> request = new HashMap<>();
        request.put("id", id);
        request.put("title", title);

        put("/api/v1/categories/" + id, request);
    }

    public void removeCategory(final Long id) {
        delete("/api/v1/categories/" + id);
    }
}

