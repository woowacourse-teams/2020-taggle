package com.woowacourse.taggle.tag.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CategoryTagsResponse {

    private Long id;
    private String title;
    private List<TagResponse> tags;

    public static CategoryTagsResponse of(final Category category, final List<Tag> tags) {
        return new CategoryTagsResponse(category.getId(), category.getTitle(),
                TagResponse.asList(tags));
    }

    public static CategoryTagsResponse ofNoCategory(final List<Tag> tags) {
        return new CategoryTagsResponse(null, null,
                TagResponse.asList(tags));
    }

    public static List<CategoryTagsResponse> asList(final List<Tag> tags,
            final List<Category> categories) {
        final List<CategoryTagsResponse> totalCategoryTagsResponses = createNoCategoryTagsResponses(tags);
        final List<CategoryTagsResponse> categoryTagsResponses = createCategoryTagsResponses(tags, categories);
        totalCategoryTagsResponses.addAll(categoryTagsResponses);

        return totalCategoryTagsResponses;
    }

    private static List<CategoryTagsResponse> createNoCategoryTagsResponses(final List<Tag> tags) {
        final List<CategoryTagsResponse> noCategoryTagsResponses = new ArrayList<>();

        final List<Tag> tagsWithoutCategory = tags.stream()
                .filter(tag -> tag.getCategory() == null)
                .collect(Collectors.toList());

        if (tagsWithoutCategory.size() > 0) {
            final CategoryTagsResponse categoryTagsResponse = CategoryTagsResponse.ofNoCategory(tagsWithoutCategory);
            noCategoryTagsResponses.add(categoryTagsResponse);
        }

        return noCategoryTagsResponses;
    }

    private static List<CategoryTagsResponse> createCategoryTagsResponses(final List<Tag> tags,
            final List<Category> categories) {
        final Map<Category, List<Tag>> cache = initCache(categories);

        tags.forEach(tag -> {
            if (tag.getCategory() != null && cache.containsKey(tag.getCategory())) {
                cache.get(tag.getCategory()).add(tag);
            }
        });

        return cache.keySet().stream()
                .map(category -> CategoryTagsResponse.of(category, cache.get(category)))
                .collect(Collectors.toList());
    }

    private static Map<Category, List<Tag>> initCache(final List<Category> categories) {
        final Map<Category, List<Tag>> cache = new HashMap<>();
        for (final Category category : categories) {
            final List<Tag> emptyTags = new ArrayList<>();
            cache.put(category, emptyTags);
        }
        return cache;
    }
}
