package com.woowacourse.taggle.tag.dto;

import javax.validation.constraints.NotEmpty;

import com.woowacourse.taggle.tag.domain.Bookmark;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BookmarkCreateRequest {

    @NotEmpty
    private String url;

    public Bookmark toEntity() {
        return new Bookmark(url);
    }
}
