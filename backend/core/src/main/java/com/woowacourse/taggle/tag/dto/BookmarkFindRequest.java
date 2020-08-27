package com.woowacourse.taggle.tag.dto;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.woowacourse.taggle.tag.exception.InvalidPageRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BookmarkFindRequest {

    private static final int DEFAULT_PAGE_LIMIT = 10;
    private static final int DEFAULT_PAGE_OFFSET = 1;
    private static final int MIN_DISPLAY_LIMIT = 1;
    private static final int MAX_DISPLAY_LIMIT = 100;

    private Integer offset;
    private Integer limit;

    public Pageable toPageable() {
        adjustOffset();
        adjustLimit();
        validate();
        return PageRequest.of(offset - 1, limit, Sort.by(Sort.Direction.DESC, "id"));
    }

    private void adjustOffset() {
        if (Objects.isNull(offset)) {
            offset = DEFAULT_PAGE_OFFSET;
        }
    }

    private void adjustLimit() {
        if (Objects.isNull(limit)) {
            limit = DEFAULT_PAGE_LIMIT;
        }
    }

    private void validate() {
        if (offset < DEFAULT_PAGE_OFFSET) {
            throw new InvalidPageRequestException("페이지 시작 위치가 올바르지 않습니다.");
        }
        if (limit < MIN_DISPLAY_LIMIT || limit > MAX_DISPLAY_LIMIT) {
            throw new InvalidPageRequestException("검색 결과 출력 건수가 올바르지 않습니다.");
        }
    }
}
