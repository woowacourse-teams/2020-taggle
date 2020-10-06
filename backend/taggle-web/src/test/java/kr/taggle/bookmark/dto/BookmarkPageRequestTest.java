package kr.taggle.bookmark.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.Pageable;

import kr.taggle.common.exception.InvalidPageRequestException;

public class BookmarkPageRequestTest {

    @DisplayName("toPageable: Pageable 생성")
    @Test
    void toPageable() {
        // given
        final BookmarkPageRequest bookmarkPageRequest = new BookmarkPageRequest(10, 3);

        // when
        final Pageable pageable = bookmarkPageRequest.toPageable();

        // then
        assertAll(() -> {
            assertThat(pageable.getOffset()).isEqualTo(27);
            assertThat(pageable.getPageSize()).isEqualTo(3);
        });
    }

    @DisplayName("toPageable: offset이 범위를 벗어난 경우 예외 발생")
    @Test
    void toPageable_InvalidOffset_ExceptionThrown() {
        // given
        final BookmarkPageRequest bookmarkPageRequest = new BookmarkPageRequest(0, 3);

        // when
        // then
        assertThatThrownBy(bookmarkPageRequest::toPageable)
                .isInstanceOf(InvalidPageRequestException.class);
    }

    @DisplayName("toPageable: limit가 범위를 벗어난 경우 예외 발생")
    @ValueSource(ints = {0, 101})
    @ParameterizedTest
    void toPageable_InvalidLimit_ExceptionThrown(final int limit) {
        // given
        final BookmarkPageRequest bookmarkPageRequest = new BookmarkPageRequest(1, limit);

        // when
        // then
        assertThatThrownBy(bookmarkPageRequest::toPageable)
                .isInstanceOf(InvalidPageRequestException.class);
    }

    @DisplayName("toPageable: offset이 주어지지 않은 경우 기본 offset을 가지는 Pageable 생성")
    @Test
    void toPageable_OffsetIsNull_ReturnPageable() {
        // given
        final BookmarkPageRequest bookmarkPageRequest = new BookmarkPageRequest(null, 3);

        // when
        final Pageable pageable = bookmarkPageRequest.toPageable();

        // then
        assertThat(pageable.getOffset()).isEqualTo(0);
    }

    @DisplayName("toPageable: limit가 주어지지 않은 경우 기본 limit를 가지는 Pageable 생성")
    @Test
    void toPageable_LimitIsNull_ReturnPageable() {
        // given
        final BookmarkPageRequest bookmarkPageRequest = new BookmarkPageRequest(3, null);

        // when
        final Pageable pageable = bookmarkPageRequest.toPageable();

        // then
        assertThat(pageable.getPageSize()).isEqualTo(10);
    }
}
