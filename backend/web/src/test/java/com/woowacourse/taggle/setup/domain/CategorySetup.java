package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategorySetup {

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public Category save(final User user) {
        return categoryRepository.save(new Category("project", user));
    }

    public Category saveWithTag(final User user) {
        System.out.println(user.getId() + "@@@@@@@@@@@@@@@@@@@@@2");
        final Category category = categoryRepository.save(new Category("project", user));
        final Tag tag = tagRepository.save(new Tag("taggle", user));
        tag.updateCategory(category);
        return category;
    }
}
