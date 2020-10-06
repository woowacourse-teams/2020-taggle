package kr.taggle.bookmark.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.bookmark.domain.TagBookmark;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BookmarkResponse {

    private Long id;
    private String url;
    private String title;
    private String description;
    private String image;

    public static BookmarkResponse of(final Bookmark bookmark) {
        return new BookmarkResponse(bookmark.getId(), bookmark.getUrl(), bookmark.getTitle(), bookmark.getTitle(),
                bookmark.getImage());
    }

    public static BookmarkResponse of(final TagBookmark tagBookmark) {
        return new BookmarkResponse(tagBookmark.getBookmark().getId(), tagBookmark.getBookmark().getUrl(),
                tagBookmark.getBookmark().getTitle(), tagBookmark.getBookmark().getDescription(),
                tagBookmark.getBookmark().getImage());
    }

    public static List<BookmarkResponse> asList(final List<Bookmark> bookmarks) {
        return bookmarks.stream()
                .map(BookmarkResponse::of)
                .collect(Collectors.toList());
    }

    public static List<BookmarkResponse> asList(final Collection<TagBookmark> bookmarks) {
        return bookmarks.stream()
                .map(BookmarkResponse::of)
                .collect(Collectors.toList());
    }
}
