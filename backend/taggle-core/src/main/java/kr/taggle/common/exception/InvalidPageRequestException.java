package kr.taggle.common.exception;

public class InvalidPageRequestException extends RuntimeException {

    public InvalidPageRequestException(final String message) {
        super(message);
    }
}
