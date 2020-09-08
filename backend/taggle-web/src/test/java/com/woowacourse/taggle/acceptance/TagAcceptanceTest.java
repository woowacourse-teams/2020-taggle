package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.bookmark.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagResponse;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;

public class TagAcceptanceTest extends AcceptanceTest {

    @Transactional
    @Test
    void manageBookmark() {
        // 태그를 생성한다
        final TagResponse tagResponse = createTag("taggle");

        assertThat(tagResponse.getName()).isEqualTo("taggle");

        // 이미 존재하는 태그와 같은 이름의 태그 생성요청시 새 태그를 생성하지 않는다.
        final TagResponse sameNameTagResponse = createTag("taggle");
        final TagBookmarkResponse sameNameTagBookmarkResponse = findBookmarksByTagId(sameNameTagResponse.getId());

        assertThat(tagResponse.getId()).isEqualTo(sameNameTagBookmarkResponse.getId());

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

    public TagBookmarkResponse findBookmarksByTagId(final Long id) {
        return get("/api/v1/tags/" + id + "/bookmarks", TagBookmarkResponse.class);
    }

    public ExtractableResponse<MockMvcResponse> findBookmarksOfTagExtractableResponse(final Long id) {
        return getExtractableResponse("/api/v1/tags/" + id + "/bookmarks");
    }

    public void deleteTeg(final Long id) {
        delete("/api/v1/tags/" + id);
    }
}

