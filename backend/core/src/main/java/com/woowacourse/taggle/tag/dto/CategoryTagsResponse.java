package com.woowacourse.taggle.tag.dto;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return new CategoryTagsResponse(null, "Uncategorized",
                TagResponse.asList(tags));
    }

    public static List<CategoryTagsResponse> asList(final List<Tag> uncategorizedTags, final List<Category> categories,
            final List<Tag> categorizedTags) {
        final CategoryTagsResponse totalCategoryTagsResponses = ofNoCategory(uncategorizedTags);
        final List<CategoryTagsResponse> categoryTagsResponses = createCategoryTagsResponses(categories,
                categorizedTags);

        categoryTagsResponses.add(0, totalCategoryTagsResponses);
        return categoryTagsResponses;
    }

    private static List<CategoryTagsResponse> createCategoryTagsResponses(final List<Category> categories,
            final List<Tag> categorizedTags) {
        Map<Category, List<Tag>> collectedTags = categorizedTags.stream()
                .collect(groupingBy(Tag::getCategory));
        List<CategoryTagsResponse> categoryTagsResponses = new ArrayList<>();
        for (Category category : categories) {
            if (collectedTags.containsKey(category)) {
                categoryTagsResponses.add(of(category, collectedTags.get(category)));
            } else {
                categoryTagsResponses.add(of(category, new ArrayList<>()));
            }
        }
        return categoryTagsResponses;
    }
}
