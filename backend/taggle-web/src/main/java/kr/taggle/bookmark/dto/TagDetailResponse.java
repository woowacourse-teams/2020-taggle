package kr.taggle.bookmark.dto;

import java.util.List;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.bookmark.domain.TagBookmark;
import kr.taggle.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TagDetailResponse {

    private Long id;
    private String name;
    private List<BookmarkResponse> bookmarks;

    public static TagDetailResponse of(final Tag tag, final List<TagBookmark> bookmarks) {
        return new TagDetailResponse(tag.getId(), tag.getName(), BookmarkResponse.asList(bookmarks));
    }

    public static TagDetailResponse ofUntagged(final List<Bookmark> bookmarks) {
        return new TagDetailResponse(null, "Untagged", BookmarkResponse.asList(bookmarks));
    }
}
