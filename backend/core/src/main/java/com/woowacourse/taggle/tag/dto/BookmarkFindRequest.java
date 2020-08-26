package com.woowacourse.taggle.tag.dto;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BookmarkFindRequest {

    private static final int DEFAULT_PAGE_SIZE = 24;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int MIN_DISPLAY_SIZE = 1;
    private static final int MAX_DISPLAY_SIZE = 100;

    private Integer start;

    private Integer display;

    public Pageable toPageable() {
        if (Objects.isNull(start)) {
            start = DEFAULT_PAGE_NUMBER;
        }
        if (start < DEFAULT_PAGE_NUMBER) {
            throw new IllegalArgumentException("페이지 시작 위치가 올바르지 않습니다.");
        }
        if (Objects.isNull(display)) {
            display = DEFAULT_PAGE_SIZE;
        }
        if (display < MIN_DISPLAY_SIZE || display > MAX_DISPLAY_SIZE) {
            throw new IllegalArgumentException("검색 결과 출력 건수가 올바르지 않습니다.");
        }
        return PageRequest.of(start - 1, display, Sort.by(Sort.Direction.DESC, "id"));
    }
}
