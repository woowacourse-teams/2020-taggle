package com.woowacourse.taggle.tag.exception;

public class BookmarkNotFoundException extends RuntimeException {
    public BookmarkNotFoundException(final String message) {
        super(message);
    }
}
