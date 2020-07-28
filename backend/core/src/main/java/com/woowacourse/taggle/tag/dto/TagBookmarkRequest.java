package com.woowacourse.taggle.tag.dto;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TagBookmarkRequest {

    @NotNull
    private Long tagId;

    @NotNull
    private Long bookmarkId;
}
