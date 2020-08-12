package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.setup.domain.BookmarkSetup;
import com.woowacourse.taggle.setup.domain.CategorySetup;
import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.dto.CategoryTagsResponse;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagResponse;

public class TagAcceptanceTest extends AcceptanceTest {

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @Autowired
    private CategorySetup categorySetup;

    @Transactional
    @WithMockUser(roles = "ADMIN")
    @Test
    void manageBookmark() {
        // 태그를 생성한다
        createTag("taggle");
        List<TagResponse> tags = findTags();

        assertThat(tags).hasSize(1);

        // 북마크에 태그를 추가한다
        final Long tagId = tags.get(0).getId();
        final Bookmark bookmark = bookmarkSetup.save();
        addBookmarkOnTag(tagId, bookmark.getId());
        final TagBookmarkResponse tagBookmarkResponse = findTagById(tagId);

        assertThat(tagBookmarkResponse.getBookmarks()).hasSize(1);

        // 태그의 카테고리를 수정한다
        final Category category = categorySetup.save();
        updateCategoryOnTag(tagId, category.getId());
        final CategoryTagsResponse categoryTagsResponse = findCategories().get(1);

        assertThat(categoryTagsResponse.getTags()).hasSize(1);
        assertThat(categoryTagsResponse.getTags().get(0).getName()).isEqualTo("taggle");

        // 태그를 제거한다
        deleteTeg(tags.get(0).getId());
        tags = findTags();

        assertThat(tags).hasSize(0);
    }

    public void createTag(final String name) {
        final Map<String, String> request = new HashMap<>();
        request.put("name", name);

        post("/api/v1/tags", request, "/api/v1/tags");
    }

    public List<TagResponse> findTags() {
        return getAsList("/api/v1/tags", TagResponse.class);
    }

    public TagBookmarkResponse findTagById(final Long id) {
        return get("/api/v1/tags/" + id + "/bookmarks", TagBookmarkResponse.class);
    }

    public void deleteTeg(final Long id) {
        delete("/api/v1/tags/" + id);
    }

    public void addBookmarkOnTag(final Long tagId, final Long bookmarkId) {
        post("/api/v1/tags/" + tagId + "/bookmarks/" + bookmarkId, new HashMap<>(), "/api/v1/tags/");
    }

    public void updateCategoryOnTag(final Long tagId, final Long categoryId) {
        put("/api/v1/tags/" + tagId + "/categories/" + categoryId, new HashMap<>());
    }

    public List<CategoryTagsResponse> findCategories() {
        return getAsList("/api/v1/categories/tags", CategoryTagsResponse.class);
    }
}
