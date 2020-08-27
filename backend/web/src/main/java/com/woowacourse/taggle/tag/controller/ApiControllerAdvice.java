package com.woowacourse.taggle.tag.controller;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.woowacourse.taggle.exception.CrawlerConnectionException;
import com.woowacourse.taggle.exception.InvalidURLException;
import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;
import com.woowacourse.taggle.tag.exception.TagBookmarkNotFoundException;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import com.woowacourse.taggle.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<String> handleTagNotFoundException(final Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("태그를 찾을 수 없습니다.");
    }

    @ExceptionHandler(BookmarkNotFoundException.class)
    public ResponseEntity<String> handleBookmarkNotFoundException(final Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("북마크를 찾을 수 없습니다.");
    }

    @ExceptionHandler(TagBookmarkNotFoundException.class)
    public ResponseEntity<String> handleTagBookmarkNotFoundException(final Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("태그에 속한 북마크를 찾을 수 없습니다.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(final Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("사용자를 찾을 수 없습니다.");
    }

    @ExceptionHandler(CrawlerConnectionException.class)
    public ResponseEntity<String> crawlerConnectionException(final CrawlerConnectionException exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Link Preview를 위한 Cralwer에 연결할 수 없습니다.");
    }

    @ExceptionHandler(InvalidURLException.class)
    public ResponseEntity<String> InvalidURLException(final InvalidURLException exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("잘못된 URL 입니다.");
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
        log.error(authenticationException.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("인증되지 않은 사용자 입니다.");
    }
}
