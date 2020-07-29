package com.woowacourse.taggle.tag.dto;

import com.woowacourse.taggle.tag.domain.Tag;
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
    // private List<BookmarkResponse> bookmarks;

    public static TagBookmarkResponse of(final Tag tag) {
        return new TagBookmarkResponse(tag.getId(), tag.getName());
    }
}
