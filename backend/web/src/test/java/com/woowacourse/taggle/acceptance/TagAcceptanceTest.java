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
        Map<String, Object> taggle = createTag("taggle");
        TagBookmarkResponse tags = findTagById((Long)taggle.get("id"));

        assertThat(tags.getName()).isEqualTo("taggle");

        // 북마크에 태그를 추가한다
        final Long tagId = tags.getId();
        final Bookmark bookmark = bookmarkSetup.save();
        addBookmarkOnTag(tagId, bookmark.getId());
        final TagBookmarkResponse tagBookmarkResponse = findTagById(tagId);

        assertThat(tagBookmarkResponse.getBookmarks()).hasSize(1);

        // // 태그의 카테고리를 수정한다
        // final Category category = categorySetup.save();
        // updateCategoryOnTag(category.getId(), tagId);
        // final CategoryTagsResponse categoryDetailResponse = findCategories().get(1);
        //
        // assertThat(categoryDetailResponse.getTags()).hasSize(1);
        // assertThat(categoryDetailResponse.getTags().get(0).getName()).isEqualTo("taggle");

        // 태그를 제거한다
        deleteTeg(tags.getId());

        // assertThat(tagRepository.findAll()).hasSize(0);
    }

    public Map<String, Object> createTag(final String name) {

        final Map<String, Object> request = new HashMap<>();
        request.put("id", 2L);
        request.put("name", name);

        post("/api/v1/tags", request, "/api/v1/tags");
        return request;
    }

    public List<TagResponse> findTags() {
        //TODO: 전체 조회 없어서 삭제 해야되는거 확인해야함
        return getAsList("/api/v1/tags", TagResponse.class);
    }

    public TagBookmarkResponse findTagById(final Long id) {
        return get("/api/v1/tags/" + id, TagBookmarkResponse.class);
    }

    public void deleteTeg(final Long id) {
        delete("/api/v1/tags/" + id);
    }

    public void addBookmarkOnTag(final Long tagId, final Long bookmarkId) {
        post("/api/v1/tags/" + tagId + "/bookmarks/" + bookmarkId, new HashMap<>(), "/api/v1/tags/");
    }

    public void updateCategoryOnTag(final Long tagId, final Long categoryId) {
        put("/api/v1/categories/" + categoryId + "/tags/" + tagId, new HashMap<>());
    }

    public List<CategoryTagsResponse> findCategories() {
        return getAsList("/api/v1/categories/tags", CategoryTagsResponse.class);
    }
}

