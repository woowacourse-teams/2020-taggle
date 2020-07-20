package com.woowacourse.taggle.tag.dto;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Set;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Getter
public class BookmarkResponse {
    private Long id;
    private List<String> tagNames;

    public static List<BookmarkResponse> listOf(final Set<TagBookmark> bookmarks) {
        return bookmarks.stream()
                .map(tag -> BookmarkResponse.ofBookmark(tag.getBookmark()))
                .collect(toList());
    }

    public static BookmarkResponse ofBookmark(final Bookmark bookmark) {
        return new BookmarkResponse(bookmark.getId(), createTagNames(bookmark.getTags()));
    }

    private static List<String> createTagNames(final Set<TagBookmark> tags) {
        return tags.stream()
                .map(tag -> tag.getTag().getName())
                .collect(toList());
    }
}
