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
        return categoryRepository.save(Category.builder()
                .title("project")
                .user(user)
                .build());
    }

    public void saveWithTag(final User user) {
        final Category category1 = categoryRepository.save(Category.builder()
                .title("project1")
                .user(user)
                .build());
        final Tag tag = tagRepository.save(Tag.builder()
                .name("taggle1")
                .user(user)
                .build());
        categoryRepository.save(Category.builder()
                .title("project2")
                .user(user)
                .build());
        tagRepository.save(Tag.builder()
                .name("taggle2")
                .user(user)
                .build());
        tag.updateCategory(category1);
    }
}
