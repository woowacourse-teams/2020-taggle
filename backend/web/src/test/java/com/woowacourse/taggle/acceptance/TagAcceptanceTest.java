package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagResponse;

public class TagAcceptanceTest extends AcceptanceTest {

    @Transactional
    @WithMockUser(username = "tigger", password = "tigger", roles = "ADMIN")
    @Test
    void manageBookmark() {

        // 태그를 생성한다
        final TagResponse tagResponse = createTag("taggle");

        assertThat(tagResponse.getName()).isEqualTo("taggle");

        // 태그를 제거한다
        deleteTeg(tagResponse.getId());
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
}

