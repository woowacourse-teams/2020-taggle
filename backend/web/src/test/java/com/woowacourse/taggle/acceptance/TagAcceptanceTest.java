package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.dto.CategoryTagsResponse;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagResponse;

public class TagAcceptanceTest extends AcceptanceTest {

    @Transactional
    @WithMockUser(username = "tigger", password = "tigger", roles = "ADMIN")
    @Test
    void manageBookmark() {

        // 태그를 생성한다
        final TagResponse taggle = createTag("taggle");
        final TagBookmarkResponse tagBookmarkResponse = findBookmarksOfTag(taggle.getId());

        assertThat(tagBookmarkResponse.getName()).isEqualTo("taggle");

        // 북마크에 태그를 추가한다
        final Long tagId = tagBookmarkResponse.getId();

        final BookmarkResponse bookmark = createBookmark("http://naver.com");
        addBookmarkOnTag(tagId, bookmark.getId());
        final TagBookmarkResponse tagBookmarkResponse2 = findBookmarksOfTag(tagId);

        assertThat(tagBookmarkResponse2.getBookmarks()).hasSize(1);

        // // 태그의 카테고리를 수정한다
        // final Category category = categorySetup.save();
        // updateCategoryOnTag(category.getId(), tagId);
        // final CategoryTagsResponse categoryDetailResponse = findCategories().get(1);
        //
        // assertThat(categoryDetailResponse.getTags()).hasSize(1);
        // assertThat(categoryDetailResponse.getTags().get(0).getName()).isEqualTo("taggle");

        // 북마크의 태그를 수정 한다
        // 태그를 제거한다
        deleteTeg(tagBookmarkResponse.getId());

        // 북마크에 새로운 태그를 추가한다
        final TagResponse tag = createTag("taggle");
        final TagBookmarkResponse anotherTagBookmarkResponse = findBookmarksOfTag(tag.getId());
        final Long anotherTagId = anotherTagBookmarkResponse.getId();
        final BookmarkResponse anotherBookmark = createBookmark("http://daum.net");
        addBookmarkOnTag(anotherTagId, anotherBookmark.getId());
        final TagBookmarkResponse anotherTagBookmarkResponse2 = findBookmarksOfTag(anotherTagId);
        assertThat(anotherTagBookmarkResponse2.getBookmarks()).hasSize(1);
    }

    public TagResponse createTag(final String name) {

        final Map<String, Object> request = new HashMap<>();
        request.put("name", name);

        return post("/api/v1/tags", request, TagResponse.class, "/api/v1/tags");
    }

    public TagBookmarkResponse findBookmarksOfTag(final Long id) {
        return get("/api/v1/tags/" + id + "/bookmarks", TagBookmarkResponse.class);
    }

    public void deleteTeg(final Long id) {
        delete("/api/v1/tags/" + id);
    }

    public void addBookmarkOnTag(final Long tagId, final Long bookmarkId) {
        post("/api/v1/tags/" + tagId + "/bookmarks/" + bookmarkId, new HashMap<>(),
                "/api/v1/tags/" + tagId + "/bookmarks/" + bookmarkId);
    }

    public List<CategoryTagsResponse> findCategories() {
        return getAsList("/api/v1/categories/tags", CategoryTagsResponse.class);
    }

    public BookmarkResponse createBookmark(final String url) {
        final Map<String, Object> request = new HashMap<>();
        request.put("url", url);

        return post("/api/v1/bookmarks", request, BookmarkResponse.class, "/api/v1/bookmarks");
    }

}

