package com.woowacourse.taggle.tag.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitleAndUserId(String title, Long userId);

    List<Category> findByUserId(final Long id);

    Optional<Category> findByIdAndUserId(Long id, Long userId);
}
