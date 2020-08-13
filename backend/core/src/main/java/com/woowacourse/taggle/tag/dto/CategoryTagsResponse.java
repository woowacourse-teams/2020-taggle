package com.woowacourse.taggle.tag.dto;

import java.util.List;

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

}
