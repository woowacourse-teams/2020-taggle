package kr.taggle.exception;

public class CrawlerConnectionException extends RuntimeException {
    public CrawlerConnectionException(String message) {
        super(message);
    }
}
