package com.woowacourse.taggle.bookmark.exception;

public class BookmarkNotFoundException extends RuntimeException {

    public BookmarkNotFoundException(final String message) {
        super(message);
    }
}
