package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategorySetup {

    private final CategoryRepository categoryRepository;

    public Category save() {
        Category project = new Category("project");
        project.add(new Tag("someTag"));
        project.add(new Tag("otherTag"));
        return categoryRepository.save(project);
    }
}
