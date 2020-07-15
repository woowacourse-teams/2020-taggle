package com.woowacourse.taggle.bookmark.domain;

import java.util.ArrayList;
import java.util.List;

import com.woowacourse.taggle.bookmark.exception.AlreadyExistBookmarkException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Bookmarks {
    private List<Bookmark> bookmarks;

    public static Bookmarks empty() {
        return new Bookmarks(new ArrayList<>());
    }

    public void addBookmark(final Bookmark bookmark) {
        validateBookmarkExist(bookmark);
        bookmarks.add(bookmark);
    }

    private void validateBookmarkExist(final Bookmark bookmark) {
        boolean hasSameTag = bookmarks.stream()
                .anyMatch(existTag -> existTag.isSameUrl(bookmark));
        if (hasSameTag) {
            throw new AlreadyExistBookmarkException("이미 존재하는 북마크입니다.\n"
                    + "bookmark:" + bookmark);
        }
    }
}
