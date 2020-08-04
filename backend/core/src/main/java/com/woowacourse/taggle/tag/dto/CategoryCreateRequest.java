package com.woowacourse.taggle.tag.dto;

import javax.validation.constraints.NotEmpty;

import com.woowacourse.taggle.tag.domain.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CategoryCreateRequest {

    @NotEmpty
    private String title;

    public Category toEntity() {
        return new Category(title);
    }
}
