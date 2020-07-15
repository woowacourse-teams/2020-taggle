package com.woowacourse.taggle.bookmark.exception;

public class AlreadyExistBookmarkException extends RuntimeException {
    public AlreadyExistBookmarkException(String message) {
        super(message);
    }
}
