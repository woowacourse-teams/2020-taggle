package kr.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.dto.TagBookmarkResponse;
import kr.taggle.category.dto.CategoryResponse;
import kr.taggle.tag.dto.TagResponse;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import kr.taggle.tag.dto.TagsResponse;

public class TagAcceptanceTest extends AcceptanceTest {

    @Transactional
    @Test
    void manageBookmark() {
        // 태그를 생성한다
        final String tagName = "taggle";
        final TagResponse tagResponse = createTag(tagName);

        assertThat(tagResponse.getName()).isEqualTo(tagName);

        // 이미 존재하는 태그와 같은 이름의 태그 생성요청시 새 태그를 생성하지 않는다.
        final TagResponse sameNameTagResponse = createTag(tagName);
        final TagBookmarkResponse sameNameTagBookmarkResponse = findBookmarksByTagId(sameNameTagResponse.getId());

        assertThat(tagResponse.getId()).isEqualTo(sameNameTagBookmarkResponse.getId());

        // 태그를 다른 카테고리로 이동한다.
        final String categoryName = "eastjun";
        final CategoryResponse categoryResponse = createCategory(categoryName);
        updateTag(tagResponse.getId(), categoryResponse.getId());
        List<TagsResponse> categories = findCategories();

        assertThat(categories.get(1).getTitle()).isEqualTo(categoryName);
        assertThat(categories.get(1).getTags()).hasSize(1);
        assertThat(categories.get(1).getTags().get(0).getName()).isEqualTo(tagName);

        // 태그를 제거한다
        deleteTeg(tagResponse.getId());
        final ExtractableResponse<MockMvcResponse> mockMvcResponse = findBookmarksOfTagExtractableResponse(
                tagResponse.getId());

        assertThat(mockMvcResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    public TagResponse createTag(final String name) {
        final Map<String, Object> request = new HashMap<>();
        request.put("name", name);

        return post("/api/v1/tags", request, TagResponse.class, "/api/v1/tags");
    }

    public void updateTag(final Long tagId, final Long categoryId) {
        final Map<String, Object> request = new HashMap<>();
        request.put("categoryId", categoryId);

        put("/api/v1/tags/" + tagId, request);
    }

    public TagBookmarkResponse findBookmarksByTagId(final Long id) {
        return get("/api/v1/tags/" + id + "/bookmarks", TagBookmarkResponse.class);
    }

    public ExtractableResponse<MockMvcResponse> findBookmarksOfTagExtractableResponse(final Long id) {
        return getExtractableResponse("/api/v1/tags/" + id + "/bookmarks");
    }

    public void deleteTeg(final Long id) {
        delete("/api/v1/tags/" + id);
    }

    private CategoryResponse createCategory(final String title) {
        final Map<String, Object> request = new HashMap<>();
        request.put("id", 1L);
        request.put("title", title);

        return post("/api/v1/categories", request, CategoryResponse.class, "/api/v1/categories");
    }

    public List<TagsResponse> findCategories() {
        return getAsList("/api/v1/categories", TagsResponse.class);
    }
}

