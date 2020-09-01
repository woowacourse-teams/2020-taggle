package com.woowacourse.taggle.tag.dto;

import java.util.List;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TagBookmarkResponse {

    private Long id;
    private String name;
    private List<BookmarkResponse> bookmarks;

    public static TagBookmarkResponse of(final Tag tag, final List<TagBookmark> bookmarks) {
        return new TagBookmarkResponse(tag.getId(), tag.getName(), BookmarkResponse.asList(bookmarks));
    }

    public static TagBookmarkResponse ofUntagged(final List<Bookmark> bookmarks) {
        return new TagBookmarkResponse(null, "Untagged", BookmarkResponse.asList(bookmarks));
    }
}
