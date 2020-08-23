package com.woowacourse.taggle.linkpreview;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LinkPreview {

    private String title;
    private String description;
    private String image;
}
