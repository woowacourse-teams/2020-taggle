package com.woowacourse.taggle.tag.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.woowacourse.taggle.bookmark.domain.TagBookmark;
import com.woowacourse.taggle.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TagResponse {

    private Long id;
    private String name;

    public static TagResponse of(final Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }

    public static TagResponse of(final TagBookmark tagBookmark) {
        return new TagResponse(tagBookmark.getTag().getId(), tagBookmark.getTag().getName());
    }

    public static List<TagResponse> asList(final List<Tag> tags) {
        return tags.stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }

    public static List<TagResponse> asList(final Set<TagBookmark> tagBookmarks) {
        return tagBookmarks.stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }
}
