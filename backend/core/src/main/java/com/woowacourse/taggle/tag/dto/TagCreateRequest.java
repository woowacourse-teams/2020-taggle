package com.woowacourse.taggle.tag.dto;

import javax.validation.constraints.NotEmpty;

import com.woowacourse.taggle.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TagCreateRequest {
    @NotEmpty
    private String name;

    public Tag toEntity() {
        return new Tag(name);
    }
}
