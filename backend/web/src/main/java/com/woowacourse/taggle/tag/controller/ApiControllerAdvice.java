package com.woowacourse.taggle.tag.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.woowacourse.taggle.tag.exception.BookmarkNotFoundException;
import com.woowacourse.taggle.tag.exception.TagBookmarkNotFoundException;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<String> handleTagNotFoundException(Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("태그를 찾을 수 없습니다.");
    }

    @ExceptionHandler(BookmarkNotFoundException.class)
    public ResponseEntity<String> handleBookmarkNotFoundException(Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("북마크를 찾을 수 없습니다.");
    }

    @ExceptionHandler(TagBookmarkNotFoundException.class)
    public ResponseEntity<String> handleTagBookmarkNotFoundException(Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("태그에 속한 북마크를 찾을 수 없습니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        log.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 내부적인 문제가 발생했습니다.");
    }
}
