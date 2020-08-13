package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategorySetup {

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserSetup userSetup;

    public Category save() {
        return categoryRepository.save(new Category("project"));
    }

    public Category saveWithUser() {
        User user = userSetup.save();
        return categoryRepository.save(new Category("project", user));
    }

    public Category saveWithTags() {
        final Category project = new Category("project");
        return categoryRepository.save(project);
    }
}
