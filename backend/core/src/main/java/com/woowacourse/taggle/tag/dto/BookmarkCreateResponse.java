package com.woowacourse.taggle.tag.dto;

import com.woowacourse.taggle.tag.domain.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookmarkCreateResponse {
    private Long id;
    private String url;

    public static BookmarkCreateResponse of(final Bookmark bookmark) {
        return new BookmarkCreateResponse(bookmark.getId(), bookmark.getUrl());
    }
}
