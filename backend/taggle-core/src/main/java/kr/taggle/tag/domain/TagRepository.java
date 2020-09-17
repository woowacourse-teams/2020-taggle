package kr.taggle.tag.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByNameAndUserId(final String name, final Long userId);

    Optional<Tag> findByIdAndUserId(final Long id, final Long userId);

    List<Tag> findAllByCategoryId(final Long categoryId);

    List<Tag> findAllByUserId(final Long userId);

    List<Tag> findAllByUserIdAndCategoryIsNotNull(final Long userId);

    List<Tag> findAllByUserIdAndCategoryIsNull(final Long userId);
}
