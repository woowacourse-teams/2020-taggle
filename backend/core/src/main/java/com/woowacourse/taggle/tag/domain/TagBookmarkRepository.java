package com.woowacourse.taggle.tag.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagBookmarkRepository extends JpaRepository<TagBookmark, Long> {
    Optional<TagBookmark> findByTagAndBookmark(final Tag tag, final Bookmark bookmark);
}
