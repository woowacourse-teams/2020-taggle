package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.tag.dto.BookmarkResponse;

public class BookmarkAcceptanceTest extends AcceptanceTest {
    @WithMockUser(roles = "ADMIN")
    @Test
    void manageBookmark() {
        createBookmark("http://taggle.com");
        final List<BookmarkResponse> bookmarks = findBookmarks();

        assertThat(bookmarks).hasSize(1);
    }

    public void createBookmark(final String url) {
        final Map<String, String> request = new HashMap<>();
        request.put("url", url);

        post("/api/v1/bookmarks", request, "/api/v1/bookmarks");
    }

    public List<BookmarkResponse> findBookmarks() {
        return getAsList("/api/v1/bookmarks", BookmarkResponse.class);
    }
}
