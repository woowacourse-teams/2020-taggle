package com.wooacourse.taggle.bookmark.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.wooacourse.taggle.bookmark.exception.UrlFormatMismatchException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Link {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "(^(https?|ftp|file)://)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    public Link(String url) {
        validate(url);
        this.url = url;
    }

    private void validate(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new UrlFormatMismatchException("url이 올바르지 않습니다.\n"
                    + "url: " + url);
        }
    }
}
