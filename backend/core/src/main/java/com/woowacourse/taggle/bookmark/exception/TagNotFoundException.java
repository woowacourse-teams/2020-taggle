package com.woowacourse.taggle.bookmark.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(final String message) {
        super(message);
    }
}
