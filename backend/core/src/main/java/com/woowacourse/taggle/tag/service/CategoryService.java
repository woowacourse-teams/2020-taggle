package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.dto.CategoryDetailResponse;
import com.woowacourse.taggle.tag.dto.CategoryRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;
import com.woowacourse.taggle.tag.exception.CategoryDuplicationException;
import com.woowacourse.taggle.tag.exception.CategoryNotFoundException;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.domain.UserRepository;
import com.woowacourse.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryResponse createCategory(final SessionUser sessionUser, final CategoryRequest categoryRequest) {
        categoryRepository.findByTitle(categoryRequest.getTitle()).ifPresent(category -> {
            throw new CategoryDuplicationException("이미 존재하는 카테고리입니다. categoryTitle: " + categoryRequest.getTitle());
        });

        final User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(RuntimeException::new);
        final Category category = categoryRepository.save(categoryRequest.toEntityWithUser(user));

        return CategoryResponse.of(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDetailResponse> findCategories(final SessionUser user) {
        final List<Category> categories = categoryRepository.findByUserId(user.getId());

        return CategoryDetailResponse.asList(categories);
    }

    public void updateCategory(final SessionUser user, final Long categoryId, final CategoryRequest categoryRequest) {
        final Category category = findCategoryByUserId(user.getId(), categoryId);
        category.update(categoryRequest.toEntity());
    }

    public void removeCategory(final SessionUser user, final Long categoryId) {
        final Category category = findCategoryByUserId(user.getId(), categoryId);
        if (category.getTitle().equals("Uncategoried")) {
            throw new RuntimeException();
        }

        final Category moveCategory = categoryRepository.findByTitle("Uncategoried")
                .orElseThrow(RuntimeException::new);

        category.getTags().forEach(tag -> tag.updateCategory(moveCategory));

        categoryRepository.delete(category);
    }

    public Category findById(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다.\n"
                        + "categoryId:" + id));
    }

    private Category findCategoryByUserId(final Long userId, final Long categoryId) {
        return categoryRepository.findCategoryByUserId(userId, categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다.\n"
                        + "categoryId:" + categoryId));
    }
}
