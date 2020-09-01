package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.dto.CategoryResponse;
import com.woowacourse.taggle.tag.dto.CategoryTagsResponse;
import com.woowacourse.taggle.tag.dto.TagResponse;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @Transactional
    @Test
    void manageCategory() {
        // 카테고리를 생성한다.
        final String categoryName = "project";
        final CategoryResponse categoryResponse = createCategory(categoryName);
        assertThat(categoryResponse.getTitle()).isEqualTo(categoryName);

        // 카테고리를 가져온다.
        List<CategoryTagsResponse> categories = findCategories();
        assertThat(categories).hasSize(2);

        // 이미 존재하는 카테고리와 같은 이름의 카테고리 생성요청시 새 카테고리를 생성하지 않는다.
        createCategory(categoryName);
        categories = findCategories();

        assertThat(categories).hasSize(2);

        // 카테고리를 수정한다.
        final CategoryTagsResponse categoryTagsResponse = categories.get(1);
        final String updateTitle = "service";
        updateCategory(categoryTagsResponse.getId(), updateTitle);
        categories = findCategories();

        assertThat(categories.get(1).getTitle()).isEqualTo(updateTitle);

        // 태그를 다른 카테고리로 이동한다.
        final String tagName = "moving";
        final TagResponse tagResponse = createTag(tagName);
        updateCategoryOnTag(categoryResponse.getId(), tagResponse.getId());
        categories = findCategories();

        assertThat(categories.get(1).getTitle()).isEqualTo(updateTitle);
        assertThat(categories.get(1).getTags()).hasSize(1);
        assertThat(categories.get(1).getTags().get(0).getName()).isEqualTo(tagName);

        // 카테고리를 제거한다.
        removeCategory(categoryTagsResponse.getId());
        categories = findCategories();

        assertThat(categories).hasSize(1); // Uncategorized가 있기 때문에 sizs는 1이다.
    }

    private CategoryResponse createCategory(final String title) {
        final Map<String, Object> request = new HashMap<>();
        request.put("id", 1L);
        request.put("title", title);

        return post("/api/v1/categories", request, CategoryResponse.class, "/api/v1/categories");
    }

    public List<CategoryTagsResponse> findCategories() {
        return getAsList("/api/v1/categories", CategoryTagsResponse.class);
    }

    private void updateCategory(final Long id, final String title) {
        final Map<String, Object> request = new HashMap<>();
        request.put("id", id);
        request.put("title", title);

        put("/api/v1/categories/" + id, request);
    }

    public TagResponse createTag(final String name) {
        final Map<String, Object> request = new HashMap<>();
        request.put("name", name);

        return post("/api/v1/tags", request, TagResponse.class, "/api/v1/tags");
    }

    public void updateCategoryOnTag(final Long categoryId, final Long tagId) {
        put("/api/v1/categories/" + categoryId + "/tags/" + tagId, new HashMap<>());
    }

    public void removeCategory(final Long id) {
        delete("/api/v1/categories/" + id);
    }
}

