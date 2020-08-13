package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.dto.CategoryRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;
import com.woowacourse.taggle.tag.dto.CategoryTagsResponse;
import com.woowacourse.taggle.tag.exception.CategoryNotFoundException;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;
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
                .orElse(categoryRepository.save(categoryRequest.toEntityWithUser(user)));

        return CategoryResponse.of(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryTagsResponse> findAllWithTags(final SessionUser user) {
        final List<Tag> tags = tagService.findAllByUserId(user.getId());
        final List<Category> categories = categoryRepository.findAllByUserId(user.getId());
        return CategoryTagsResponse.asList(tags, categories);
    }

    public void updateCategory(final SessionUser user, final Long categoryId, final CategoryRequest categoryRequest) {
        final Category category = findByIdAndUserId(categoryId, user.getId());

        category.update(categoryRequest.toEntity());
    }

    public void updateCategoryOnTag(final SessionUser user, final Long categoryId, final Long tagId) {
        final Category category = findByIdAndUserId(categoryId, user.getId());
        final Tag tag = tagService.findByIdAndUserId(tagId, user.getId());

        tag.updateCategory(category);
    }

    public void removeCategory(final SessionUser user, final Long categoryId) {
        final Category category = findByIdAndUserId(categoryId, user.getId());
        tagService.findByCategoryId(categoryId).forEach(tag -> tag.updateCategory(null));

        categoryRepository.delete(category);
    }

    public Category findByIdAndUserId(final Long categoryId, final Long userId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다.\n"
                        + "categoryId:" + categoryId));
    }

}
