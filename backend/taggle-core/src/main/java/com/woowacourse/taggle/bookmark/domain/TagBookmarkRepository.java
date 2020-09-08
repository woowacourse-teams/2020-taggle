package com.woowacourse.taggle.bookmark.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowacourse.taggle.tag.domain.Tag;

@Repository
public interface TagBookmarkRepository extends JpaRepository<TagBookmark, Long> {

    Optional<TagBookmark> findByTagAndBookmark(final Tag tag, final Bookmark bookmark);

    Page<TagBookmark> findAllByTag(final Tag tag, Pageable pageable);

    void deleteAllByTag(final Tag tag);
}
