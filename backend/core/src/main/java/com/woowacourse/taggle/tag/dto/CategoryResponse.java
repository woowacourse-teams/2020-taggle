package com.woowacourse.taggle.tag.dto;

import com.woowacourse.taggle.tag.domain.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CategoryResponse {

    private Long id;
    private String title;

    public static CategoryResponse of(final Category category) {
        return new CategoryResponse(category.getId(), category.getTitle());
    }
}
