package com.woowacourse.taggle.usertag;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.woowacourse.taggle.bookmark.domain.Bookmark;
import com.woowacourse.taggle.bookmark.domain.Bookmarks;
import com.woowacourse.taggle.bookmark.domain.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class UserTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Tag tag;

    @Column(nullable = false)
    private Bookmarks bookmarks;

    public UserTag(final Tag tag) {
        this.tag = tag;
        this.bookmarks = Bookmarks.empty();
    }

    public void addBookmark(final Bookmark bookmark) {
        bookmark.addTag(tag);
        bookmarks.addBookmark(bookmark);
    }

    public void removeBookmark(final Bookmark bookmark) {
        bookmark.removeTag(tag);
        bookmarks.removeBookmark(bookmark);
    }
}
