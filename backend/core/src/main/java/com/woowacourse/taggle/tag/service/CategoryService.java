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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(final CategoryRequest categoryRequest) {
        final boolean isPresentCategory = categoryRepository.findByTitle(categoryRequest.getTitle()).isPresent();
        if (isPresentCategory) {
            throw new CategoryDuplicationException("이미 존재하는 카테고리입니다. categoryTitle: " + categoryRequest.getTitle());
        }
        final Category category = categoryRepository.save(categoryRequest.toEntity());

        return CategoryResponse.of(category);
    }

    public List<CategoryDetailResponse> findCategories() {
        final List<Category> categories = categoryRepository.findAll();
        return CategoryDetailResponse.asList(categories);
    }

    @Transactional
    public void updateCategory(final Long id, final CategoryRequest categoryRequest) {
        final Category category = findCategoryById(id);
        category.update(categoryRequest.toEntity());
    }

    public void removeCategory(final Long id) {
        final Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    private Category findCategoryById(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다.\n"
                        + "categoryId:" + id));
    }
}