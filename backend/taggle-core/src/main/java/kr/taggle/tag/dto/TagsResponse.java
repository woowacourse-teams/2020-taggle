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
public class TagsResponse {

    private Long id;
    private String title;
    private List<TagResponse> tags;

    public static TagsResponse of(final Category category, final List<Tag> tags) {
        return new TagsResponse(category.getId(), category.getTitle(),
                TagResponse.asList(tags));
    }

    public static TagsResponse ofNoCategory(final List<Tag> tags) {
        return new TagsResponse(null, "Uncategorized",
                TagResponse.asList(tags));
    }

    public static List<TagsResponse> asList(final List<Tag> uncategorizedTags, final List<Category> categories,
            final List<Tag> categorizedTags) {
        final TagsResponse totalTagsResponses = ofNoCategory(uncategorizedTags);
        final List<TagsResponse> tagsRespons = createCategoryTagsResponses(categories,
                categorizedTags);

        tagsRespons.add(0, totalTagsResponses);
        return tagsRespons;
    }

    private static List<TagsResponse> createCategoryTagsResponses(final List<Category> categories,
            final List<Tag> categorizedTags) {
        Map<Category, List<Tag>> collectedTags = categorizedTags.stream()
                .collect(groupingBy(Tag::getCategory));
        List<TagsResponse> tagsRespons = new ArrayList<>();
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
