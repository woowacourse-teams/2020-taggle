package com.woowacourse.taggle.bookmark.dto;

import com.woowacourse.taggle.bookmark.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TagResponse {
    private Long id;
    private String name;

    public static TagResponse of(final Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}
