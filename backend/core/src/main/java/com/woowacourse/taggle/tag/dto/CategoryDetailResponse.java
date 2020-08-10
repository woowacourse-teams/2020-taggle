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

    public static List<CategoryDetailResponse> asList(final List<Category> categories,
            final List<TagResponse> tagsWithoutCategory) {
        final CategoryDetailResponse noCategoryDetailResponse = ofNoCategory(tagsWithoutCategory);
        final List<CategoryDetailResponse> categoryDetailResponses = categories.stream()
                .map(CategoryDetailResponse::of)
                .collect(Collectors.toList());
        categoryDetailResponses.add(0, noCategoryDetailResponse);
        return categoryDetailResponses;
    }

    public static CategoryDetailResponse ofNoCategory(final List<TagResponse> tags) {
        return new CategoryDetailResponse(null, "No Category", tags);
    }

    private static CategoryDetailResponse of(final Category category) {
        return new CategoryDetailResponse(category.getId(), category.getTitle(),
                TagResponse.asList(new ArrayList<>(category.getTags())));
    }

}
