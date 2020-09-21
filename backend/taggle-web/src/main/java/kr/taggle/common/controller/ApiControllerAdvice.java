package kr.taggle.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.taggle.bookmark.exception.BookmarkNotFoundException;
import kr.taggle.bookmark.exception.TagBookmarkNotFoundException;
import kr.taggle.exception.CrawlerConnectionException;
import kr.taggle.exception.InvalidURLException;
import kr.taggle.tag.exception.TagNotFoundException;
import kr.taggle.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<String> handleTagNotFoundException(final Exception exception) {
        log.info(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("태그를 찾을 수 없습니다.");
    }

    @ExceptionHandler(BookmarkNotFoundException.class)
    public ResponseEntity<String> handleBookmarkNotFoundException(final Exception exception) {
        log.info(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("북마크를 찾을 수 없습니다.");
    }

    @ExceptionHandler(TagBookmarkNotFoundException.class)
    public ResponseEntity<String> handleTagBookmarkNotFoundException(final Exception exception) {
        log.info(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("태그에 속한 북마크를 찾을 수 없습니다.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(final Exception exception) {
        log.info(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("사용자를 찾을 수 없습니다.");
    }

    @ExceptionHandler(CrawlerConnectionException.class)
    public ResponseEntity<String> crawlerConnectionException(final CrawlerConnectionException exception) {
        log.info(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Link Preview를 위한 Cralwer에 연결할 수 없습니다.");
    }

    @ExceptionHandler(InvalidURLException.class)
    public ResponseEntity<String> InvalidURLException(final InvalidURLException exception) {
        log.info(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("잘못된 URL 입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        log.info(exception.getMessage());

        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(final Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 내부적인 문제가 발생했습니다.");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> authenticationException(final AuthenticationException authenticationException) {
        log.info(authenticationException.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("인증되지 않은 사용자 입니다.");
    }
}
