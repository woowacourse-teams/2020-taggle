package com.woowacourse.taggle.tag.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitle(final String title);

    List<Category> findByUserId(final Long id);

    @Query(value = "SELECT * FROM CATEGORY  WHERE USER_ID = :userId AND CATEGORY_ID = :categoryId", nativeQuery = true)
    Optional<Category> findCategoryByUserId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);
}
