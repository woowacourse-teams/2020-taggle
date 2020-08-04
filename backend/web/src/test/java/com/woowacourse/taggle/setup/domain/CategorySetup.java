package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategorySetup {

    private final CategoryRepository categoryRepository;

    public Category save() {
        return categoryRepository.save(new Category("project"));
    }
}
