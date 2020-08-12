package com.woowacourse.taggle.tag.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(final String name);

    Optional<Tag> findByIdAndUserId(Long id, Long userId);

    List<Tag> findAllByCategoryId(Long categoryId);

    List<Tag> findAllByUserId(Long userId);
}
