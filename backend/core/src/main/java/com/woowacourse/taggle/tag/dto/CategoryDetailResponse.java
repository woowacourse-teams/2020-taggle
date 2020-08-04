package com.woowacourse.taggle.tag.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.woowacourse.taggle.tag.domain.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CategoryDetailResponse {

    private Long id;
    private String title;
    private List<TagResponse> tags;

    public static List<CategoryDetailResponse> asList(List<Category> categories) {
        return categories.stream()
                .map(CategoryDetailResponse::of)
                .collect(Collectors.toList());
    }

    private static CategoryDetailResponse of(Category category) {
        return new CategoryDetailResponse(category.getId(), category.getTitle(), TagResponse.asList(new ArrayList<>(category.getTags())));
    }
}
