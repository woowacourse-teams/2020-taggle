package kr.taggle.category.exception;

public class CategoryDuplicationException extends RuntimeException {

    public CategoryDuplicationException(final String message) {
        super(message);
    }
}
