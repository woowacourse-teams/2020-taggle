package com.woowacourse.taggle.tag.dto;

import java.util.List;

import com.woowacourse.taggle.tag.domain.Bookmark;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class BookmarkTagResponse {

    private Long id;
    private String url;
    private List<TagResponse> tags;

    public static BookmarkTagResponse of(final Bookmark bookmark) {
        return new BookmarkTagResponse(bookmark.getId(), bookmark.getUrl(), TagResponse.asList(bookmark.getTags()));
    }
}
