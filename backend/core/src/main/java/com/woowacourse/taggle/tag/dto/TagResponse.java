package com.woowacourse.taggle.tag.dto;

import java.util.List;

import com.woowacourse.taggle.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TagResponse {
    private Long id;
    private String name;
    private List<BookmarkResponse> bookmarks;

    public static TagResponse of(final Tag tag) {
        return new TagResponse(tag.getId(), tag.getName(), BookmarkResponse.listOf(tag.getBookmarks()));
    }
}
