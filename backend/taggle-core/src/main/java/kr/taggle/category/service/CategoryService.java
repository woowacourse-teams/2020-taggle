package kr.taggle.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.category.domain.Category;
import kr.taggle.category.domain.CategoryRepository;
import kr.taggle.category.dto.CategoryRequest;
import kr.taggle.category.dto.CategoryResponse;
import kr.taggle.category.exception.CategoryNotFoundException;
import kr.taggle.tag.domain.Tag;
import kr.taggle.tag.dto.CategoryDetailResponse;
import kr.taggle.tag.service.TagService;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TagService tagService;
    private final UserService userService;

    public CategoryResponse createCategory(final SessionUser sessionUser, final CategoryRequest categoryRequest) {
        final User user = userService.findById(sessionUser.getId());
        final Category category = categoryRepository.findByTitleAndUserId(categoryRequest.getTitle(),
                sessionUser.getId())
                .orElseGet(() -> categoryRepository.save(categoryRequest.toEntityWithUser(user)));

        return CategoryResponse.of(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDetailResponse> findAllTagsBy(final SessionUser user) {
        final List<Tag> uncategorizedTags = tagService.findUncategorizedTagsByUserId(user.getId());
        final List<Category> categories = categoryRepository.findAllByUserIdOrderByTitle(user.getId());
        final List<Tag> categorizedTags = tagService.findCategorizedTagsByUserId(user.getId());

        return CategoryDetailResponse.asList(uncategorizedTags, categories, categorizedTags);
    }

    public void updateCategory(final SessionUser user, final Long categoryId, final CategoryRequest categoryRequest) {
        final Category category = findByIdAndUserId(categoryId, user.getId());

        category.update(categoryRequest.toEntity());
    }

    public void removeCategory(final SessionUser user, final Long categoryId) {
        final Category category = findByIdAndUserId(categoryId, user.getId());
        final List<Tag> tags = tagService.findByCategoryId(categoryId);

        for (Tag tag : tags) {
            tag.updateCategory(null);
        }
        categoryRepository.delete(category);
    }

    public Category findByIdAndUserId(final Long categoryId, final Long userId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다. categoryId:" + categoryId));
    }
}
