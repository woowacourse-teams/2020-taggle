package kr.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.category.dto.CategoryResponse;
import kr.taggle.tag.dto.CategoryDetailResponse;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @Transactional
    @Test
    void manageCategory() {
        // 카테고리를 생성한다.
        final String categoryName = "project";
        final CategoryResponse categoryResponse = createCategory(categoryName);
        assertThat(categoryResponse.getTitle()).isEqualTo(categoryName);

        // 카테고리를 가져온다.
        List<CategoryDetailResponse> categories = findCategories();
        assertThat(categories).hasSize(2);

        // 이미 존재하는 카테고리와 같은 이름의 카테고리 생성요청시 새 카테고리를 생성하지 않는다.
        createCategory(categoryName);
        categories = findCategories();

        assertThat(categories).hasSize(2);

        // 카테고리를 수정한다.
        final CategoryDetailResponse categoryDetailResponse = categories.get(1);
        final String updateTitle = "service";
        updateCategory(categoryDetailResponse.getId(), updateTitle);
        categories = findCategories();

        assertThat(categories.get(1).getTitle()).isEqualTo(updateTitle);

        // 카테고리를 제거한다.
        removeCategory(categoryDetailResponse.getId());
        categories = findCategories();

        assertThat(categories).hasSize(1); // Uncategorized가 있기 때문에 sizs는 1이다.
    }

    private CategoryResponse createCategory(final String title) {
        final Map<String, Object> request = new HashMap<>();
        request.put("id", 1L);
        request.put("title", title);

        return post("/api/v1/categories", request, CategoryResponse.class, "/api/v1/categories");
    }

    public List<CategoryDetailResponse> findCategories() {
        return getAsList("/api/v1/categories", CategoryDetailResponse.class);
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

