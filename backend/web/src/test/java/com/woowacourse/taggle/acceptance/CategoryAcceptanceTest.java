package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.dto.CategoryResponse;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @Transactional
    @WithMockUser(roles = "ADMIN")
    @Test
    void manageCategory() {
        // 카테고리를 생성한다.
        createCategory("project");
        final List<CategoryResponse> categories = findCategories();

        assertThat(categories).hasSize(1);

        // // 카테고리를 수정한다.
        // final Long categoryId = categories.get(0).getId();
        // updateCategory(categoryId, "service");
        //
        // // 카테고리를 제거한다.
        // removeCategory(categoryId);
        //
        // categories = findCategories();
        // assertThat(categories).hasSize(0);
    }

    public List<CategoryResponse> findCategories() {
        return getAsList("/api/v1/categories", CategoryResponse.class);
    }

    private void createCategory(final String title) {
        final Map<String, String> request = new HashMap<>();
        request.put("title", title);

        post("/api/v1/categories", request, "/api/v1/categories");
    }

    private void updateCategory(final Long id, final String name) {
        final Map<String, String> request = new HashMap<>();
        request.put("name", name);

        put("/api/v1/categories/" + id, request);
    }

    public void removeCategory(final Long id) {
        delete("/api/v1/categories/" + id);
    }
}
