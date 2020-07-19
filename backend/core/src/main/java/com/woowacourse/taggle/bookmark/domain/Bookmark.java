package com.woowacourse.taggle.bookmark.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.woowacourse.taggle.bookmark.exception.UrlFormatMismatchException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Entity
public class Bookmark {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "(^(https?|ftp|file)://)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private boolean isRead;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TagBookmark> tags = new HashSet<>();

    public Bookmark(final String url) {
        validateUrl(url);
        this.url = url;
        this.isRead = false;
    }

    private void validateUrl(final String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new UrlFormatMismatchException("url이 올바르지 않습니다.\n"
                    + "url: " + url);
        }
    }

    public void addTag(final Tag tag) {
        TagBookmark tagBookmark = new TagBookmark(tag, this);
        if (!tags.contains(tagBookmark)) {
            tags.add(tagBookmark);
            tag.addBookmark(this);
        }
    }

    public void removeTag(final Tag tag) {
        TagBookmark tagBookmark = new TagBookmark(tag, this);
        if (tags.contains(tagBookmark)) {
            tags.remove(tagBookmark);
            tag.removeBookmark(this);
        }
    }
}
