package kr.taggle.bookmark.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.taggle.tag.domain.Tag;

@Repository
public interface TagBookmarkRepository extends JpaRepository<TagBookmark, Long> {

    Optional<TagBookmark> findByTagAndBookmark(final Tag tag, final Bookmark bookmark);

    Page<TagBookmark> findAllByTag(final Tag tag, Pageable pageable);

    void deleteAllByTagId(final Long id);
}
