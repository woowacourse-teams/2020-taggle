package com.woowacourse.taggle.tag.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUrlAndUserId(final String url, final Long userId);

    Optional<Bookmark> findByIdAndUserId(final Long bookmarkId, final Long userId);

    List<Bookmark> findAllByUserId(final Long userId);
}
