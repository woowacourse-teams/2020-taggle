package com.woowacourse.taggle.tag.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookmarkAddRequest {
    @NotNull
    private Long tagId;

    @NotEmpty
    private String url;
}
