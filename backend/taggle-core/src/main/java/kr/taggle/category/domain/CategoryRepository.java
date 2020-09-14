package kr.taggle.category.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitleAndUserId(final String title, final Long userId);

    Optional<Category> findByIdAndUserId(final Long id, final Long userId);

    List<Category> findAllByUserIdOrderByTitle(final Long userId);
}
