package com.woowacourse.taggle.bookmark.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.woowacourse.taggle.bookmark.exception.UrlFormatMismatchException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Link {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "(^(https?|ftp|file)://)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    private String url;

    public Link(final String url) {
        validate(url);
        this.url = url;
    }

    private void validate(final String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new UrlFormatMismatchException("url이 올바르지 않습니다.\n"
                    + "url: " + url);
        }
    }
}
