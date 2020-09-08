package kr.taggle.setup.domain;

import org.springframework.stereotype.Component;

import kr.taggle.category.domain.Category;
import kr.taggle.category.domain.CategoryRepository;
import kr.taggle.tag.domain.Tag;
import kr.taggle.tag.domain.TagRepository;
import kr.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategorySetup {

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public Category save(final User user) {
        return categoryRepository.save(new Category("project", user));
    }

    public void saveWithTag(final User user) {
        final Category category1 = categoryRepository.save(new Category("project1", user));
        final Tag tag = tagRepository.save(new Tag("taggle1", user));
        categoryRepository.save(new Category("project2", user));
        tagRepository.save(new Tag("taggle2", user));
        tag.updateCategory(category1);
    }
}
