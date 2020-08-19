package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.dto.BookmarkResponse;

public class BookmarkAcceptanceTest extends AcceptanceTest {

    @Transactional
    @WithMockUser(roles = "ADMIN")
    @Test
    void manageBookmark() {
        // 북마크를 생성한다.
        final BookmarkResponse bookmarkResponse = createBookmark("http://naver.com");

        assertThat(bookmarkResponse.getUrl()).isEqualTo("http://naver.com");

        // 기존에 존재하는 북마크와 동일한 이름의 북마크를 생성요청시, 새 북마크를 생성하지 않는다.
        createBookmark("http://naver.com");
        final List<BookmarkResponse> bookmarks = findBookmarks();

        assertThat(bookmarks).hasSize(1);

        // 북마크 전체를 가져온다.
        List<BookmarkResponse> bookmarkResponses = findBookmarks();

        assertThat(bookmarkResponses).hasSize(1);

        // 북마크를 제거한다.
        removeBookmark(bookmarkResponse.getId());

        bookmarkResponses = findBookmarks();
        assertThat(bookmarkResponses).hasSize(0);
    }

    public BookmarkResponse createBookmark(final String url) {
        final Map<String, Object> request = new HashMap<>();
        request.put("url", url);

        return post("/api/v1/bookmarks", request, BookmarkResponse.class, "/api/v1/bookmarks");
    }

    public List<BookmarkResponse> findBookmarks() {
        return getAsList("/api/v1/bookmarks", BookmarkResponse.class);
    }

    public void removeBookmark(final Long id) {
        delete("/api/v1/bookmarks/" + id);
    }
}

