package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategorySetup {

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public Category save() {
        return categoryRepository.save(new Category("project"));
    }

    public Category saveWithTags() {
        Category project = new Category("project");
        project.add(tagRepository.save(new Tag("someTag")));
        project.add(tagRepository.save(new Tag("otherTag")));
        return categoryRepository.save(project);
    }
}
