package com.woowacourse.taggle.tag.dto;

import com.woowacourse.taggle.tag.domain.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookmarkCreateRequest {
    private String url;

    public Bookmark toEntity() {
        return new Bookmark(url);
    }
}
