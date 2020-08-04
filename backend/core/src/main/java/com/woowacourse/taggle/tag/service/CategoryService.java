package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.dto.CategoryRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(final CategoryRequest categoryRequest) {
        final Category category = categoryRepository.save(categoryRequest.toEntity());

        return CategoryResponse.of(category);
    }

    public List<CategoryResponse> findCategories() {
        final List<Category> categories = categoryRepository.findAll();
        return CategoryResponse.asList(categories);
    }

    @Transactional
    public void updateCategory(final Long id, final CategoryRequest categoryRequest) {
        final Category category = categoryRepository.findById(id).orElseThrow(IllegalAccessError::new);
        category.update(categoryRequest.toEntity());
    }

    public void removeCategory(final Long id) {
        final Category category = categoryRepository.findById(id).orElseThrow(IllegalAccessError::new);
        categoryRepository.delete(category);
    }
}
