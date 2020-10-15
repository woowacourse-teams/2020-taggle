package kr.taggle.tag.dto;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.taggle.category.domain.Category;
import kr.taggle.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CategoryDetailResponse {

    private Long id;
    private String title;
    private List<TagResponse> tags;

    public static CategoryDetailResponse of(final Category category, final List<Tag> tags) {
        return new CategoryDetailResponse(category.getId(), category.getTitle(),
                TagResponse.asList(tags));
    }

    public static CategoryDetailResponse ofNoCategory(final List<Tag> tags) {
        return new CategoryDetailResponse(null, "Uncategorized",
                TagResponse.asList(tags));
    }

    public static List<CategoryDetailResponse> asList(final List<Tag> uncategorizedTags, final List<Category> categories,
            final List<Tag> categorizedTags) {

        final List<CategoryDetailResponse> result = new ArrayList<>();
        result.add(ofNoCategory(uncategorizedTags));
        result.addAll(createExistingCategoryDetailResponses(categories, categorizedTags));

        return result;
    }

    private static List<CategoryDetailResponse> createExistingCategoryDetailResponses(final List<Category> categories,
            final List<Tag> categorizedTags) {
        Map<Category, List<Tag>> collectedTags = categorizedTags.stream()
                .collect(groupingBy(Tag::getCategory));
        List<CategoryDetailResponse> tagsRespons = new ArrayList<>();
        for (Category category : categories) {
            if (collectedTags.containsKey(category)) {
                tagsRespons.add(of(category, collectedTags.get(category)));
            } else {
                tagsRespons.add(of(category, new ArrayList<>()));
            }
        }
        return tagsRespons;
    }
}
