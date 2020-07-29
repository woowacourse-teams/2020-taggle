package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.tag.dto.TagResponse;

public class TagAcceptanceTest extends AcceptanceTest {
    @WithMockUser(roles = "ADMIN")
    @Test
    void manageBookmark() {
        createTag("http://taggle.com");
        final List<TagResponse> tags = findTags();

        assertThat(tags).hasSize(1);
    }

    public void createTag(final String name) {
        final Map<String, String> request = new HashMap<>();
        request.put("name", name);

        post("/api/v1/tags", request, "/api/v1/tags");
    }

    public List<TagResponse> findTags() {
        return getAsList("/api/v1/tags", TagResponse.class);
    }
}
