package kr.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.bookmark.dto.BookmarkTagResponse;
import kr.taggle.bookmark.dto.TagBookmarkResponse;
import kr.taggle.tag.dto.TagResponse;

public class TagBookmarkAcceptanceTest extends AcceptanceTest {

    @Transactional
    @Test
    void manageTagBookmark() {
        // Untagged의 북마크 목록을 불러온다.
        final BookmarkResponse bookmarkResponse = createBookmark("http://naver.com");
        TagBookmarkResponse tagBookmarkResponse = findUntaggedBookmarks();

        assertThat(tagBookmarkResponse.getId()).isNull();
        assertThat(tagBookmarkResponse.getName()).isEqualTo("Untagged");
        assertThat(tagBookmarkResponse.getBookmarks()).hasSize(1);

        // 북마크에 태그를 추가한다.
        final TagResponse tagResponse = createTag("taggle");
        addTagOnBookmark(bookmarkResponse.getId(), tagResponse.getId());

        // 방금 추가한 태그를 질의어로 활용하여 북마크 목록을 조회한다.
        tagBookmarkResponse = findBookmarksByTagId(tagResponse.getId());

        assertThat(tagBookmarkResponse.getName()).isEqualTo("taggle");
        assertThat(tagBookmarkResponse.getBookmarks()).hasSize(1);

        // 해당 북마크 단건조회를 통해 북마크에 추가된 태그를 확인한다.
        BookmarkTagResponse bookmarkTagResponse = findBookmarkDetail(bookmarkResponse.getId());

        assertThat(bookmarkTagResponse.getUrl()).isEqualTo("http://naver.com");
        assertThat(bookmarkTagResponse.getTags()).hasSize(1);

        // 북마크에 추가되어있는 태그를 삭제한다.
        removeBookmarkOnTag(bookmarkResponse.getId(), tagResponse.getId());

        bookmarkTagResponse = findBookmarkDetail(bookmarkResponse.getId());

        assertThat(bookmarkTagResponse.getUrl()).isEqualTo("http://naver.com");
        assertThat(bookmarkTagResponse.getTags()).hasSize(0);
    }

    public BookmarkResponse createBookmark(final String url) {
        final Map<String, Object> request = new HashMap<>();
        request.put("url", url);

        return post("/api/v1/bookmarks", request, BookmarkResponse.class, "/api/v1/bookmarks");
    }

    public TagResponse createTag(final String name) {
        final Map<String, Object> request = new HashMap<>();
        request.put("name", name);

        return post("/api/v1/tags", request, TagResponse.class, "/api/v1/tags");
    }

    public TagBookmarkResponse findUntaggedBookmarks() {
        return get("/api/v1/bookmarks?tag=none", TagBookmarkResponse.class);
    }

    public void addTagOnBookmark(final Long bookmarkId, final Long tagId) {
        post(String.format("/api/v1/bookmarks/%d/tags/%d", bookmarkId, tagId),
                new HashMap<>(), String.format("/api/v1/bookmarks/%d/tags/%d", bookmarkId, tagId));
    }

    public TagBookmarkResponse findBookmarksByTagId(final Long tagId) {
        return get(String.format("/api/v1/bookmarks?tag=%d", tagId), TagBookmarkResponse.class);
    }

    public BookmarkTagResponse findBookmarkDetail(final Long bookmarkId) {
        return get(String.format("/api/v1/bookmarks/%d", bookmarkId) , BookmarkTagResponse.class);
    }

    public void removeBookmarkOnTag(final Long bookmarkId, final Long tagId) {
        delete(String.format("/api/v1/bookmarks/%d/tags/%d", bookmarkId, tagId));
    }
}
