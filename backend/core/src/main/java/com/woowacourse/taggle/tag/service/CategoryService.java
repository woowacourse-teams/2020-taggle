package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.dto.CategoryCreateRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(final CategoryCreateRequest categoryCreateRequest) {
        final Category category = categoryRepository.save(categoryCreateRequest.toEntity());

        return CategoryResponse.of(category);
    }

    public List<CategoryResponse> findCategories() {
        final List<Category> categories = categoryRepository.findAll();
        return CategoryResponse.asList(categories);
    }

    public void updateCategory(final Long id, final CategoryCreateRequest categoryCreateRequest) {

    }

    public void removeCategory(final Long id) {

    }
}
