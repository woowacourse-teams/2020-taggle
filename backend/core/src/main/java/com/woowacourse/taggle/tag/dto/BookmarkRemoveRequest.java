package com.woowacourse.taggle.tag.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BookmarkRemoveRequest {
    private Long tagId;
    private Long bookmarkId;
}
