package com.woowacourse.taggle.tag.dto;

import com.woowacourse.taggle.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TagCreateRequest {
    private String name;

    public Tag toEntity() {
        return new Tag(name);
    }
}
