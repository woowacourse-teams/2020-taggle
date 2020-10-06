package kr.taggle.bookmark.dto;

import kr.taggle.bookmark.domain.Bookmark;
import kr.taggle.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookmarkCreateDto {

    private String url;
    private String title;
    private String description;
    private String image;

    public static BookmarkCreateDto of(
            final BookmarkCreateRequest bookmarkCreateRequest, final String title, final String description,
            final String image) {
        return new BookmarkCreateDto(bookmarkCreateRequest.getUrl(), title, description, image);
    }

    public Bookmark toEntityWithUser(final User user) {
        return new Bookmark(url, user, title, description, image);
    }
}
