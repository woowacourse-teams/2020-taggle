package com.woowacourse.taggle.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookmarkRemoveRequest {
    private Long tagId;
    private Long bookmarkId;
}
