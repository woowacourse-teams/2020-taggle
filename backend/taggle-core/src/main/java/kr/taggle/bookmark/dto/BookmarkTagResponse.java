package kr.taggle.bookmark.dto;

import java.util.List;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.tag.dto.TagResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BookmarkTagResponse {

    private Long id;
    private String url;
    private List<TagResponse> tags;

    public static BookmarkTagResponse of(final Bookmark bookmark) {
        return new BookmarkTagResponse(bookmark.getId(), bookmark.getUrl(), TagResponse.asList(bookmark.getTags()));
    }
}
