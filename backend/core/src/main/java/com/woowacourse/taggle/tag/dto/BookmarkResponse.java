package com.woowacourse.taggle.tag.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.TagBookmark;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BookmarkResponse {

    private Long id;
    private String url;

    public static BookmarkResponse of(final Bookmark bookmark) {
        return new BookmarkResponse(bookmark.getId(), bookmark.getUrl());
    }

    public static BookmarkResponse of(final TagBookmark tagBookmark) {
        return new BookmarkResponse(tagBookmark.getBookmark().getId(), tagBookmark.getBookmark().getUrl());
    }

    public static List<BookmarkResponse> asList(final List<Bookmark> bookmarks) {
        return bookmarks.stream()
                .map(BookmarkResponse::of)
                .collect(Collectors.toList());
    }

    public static List<BookmarkResponse> asList(final Set<TagBookmark> bookmarks) {
        return bookmarks.stream()
                .map(BookmarkResponse::of)
                .collect(Collectors.toList());
    }
}
