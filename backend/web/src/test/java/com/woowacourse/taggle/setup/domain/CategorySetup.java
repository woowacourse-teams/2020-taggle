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
        final Category project = new Category("project");
        final Tag tag1 = tagRepository.save(new Tag("someTag"));
        final Tag tag2 = tagRepository.save(new Tag("otherTag"));
        tag1.updateCategory(project);
        tag2.updateCategory(project);
        project.add(tagRepository.save(tag1));
        project.add(tagRepository.save(tag2));
        return categoryRepository.save(project);
    }
}
