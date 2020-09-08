package com.woowacourse.taggle.bookmark.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUrlAndUserId(final String url, final Long userId);

    Optional<Bookmark> findByIdAndUserId(final Long bookmarkId, final Long userId);

    Page<Bookmark> findAllByUserId(final Long userId, final Pageable pageable);

    Page<Bookmark> findAllByUserIdAndTagsEmpty(final Long userId, final Pageable pageable);
}
