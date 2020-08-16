package com.woowacourse.taggle.tag.dto;

import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.user.domain.User;
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

    public static BookmarkCreateDto of(BookmarkCreateRequest bookmarkCreateRequest, String title, String description,
            String image) {
        return new BookmarkCreateDto(bookmarkCreateRequest.getUrl(), title, description, image);
    }

    public Bookmark toEntityWithUser(User user) {
        return new Bookmark(url, user, title, description, image);
    }
}
