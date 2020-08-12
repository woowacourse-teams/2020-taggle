package com.woowacourse.taggle.tag.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return ofTotalCategoryTagsResponses(tags, categories);
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

    private List<CategoryTagsResponse> ofTotalCategoryTagsResponses(final List<Tag> tags,
            final List<Category> categories) {
        final List<CategoryTagsResponse> totalCategoryTagsResponses = createNoCategoryTagsResponses(tags);
        final List<CategoryTagsResponse> categoryTagsResponses = createCategoryTagsResponses(tags, categories);
        totalCategoryTagsResponses.addAll(categoryTagsResponses);

        return totalCategoryTagsResponses;
    }

    private List<CategoryTagsResponse> createNoCategoryTagsResponses(final List<Tag> tags) {
        final List<CategoryTagsResponse> noCategoryTagsResponses = new ArrayList<>();

        final List<Tag> tagsWithoutCategory = tags.stream()
                .filter(tag -> tag.getCategory() == null)
                .collect(Collectors.toList());

        if (tagsWithoutCategory.size() > 0) {
            final CategoryTagsResponse categoryTagsResponse = CategoryTagsResponse.ofNoCategory(tagsWithoutCategory);
            noCategoryTagsResponses.add(categoryTagsResponse);
        }

        return noCategoryTagsResponses;
    }

    private List<CategoryTagsResponse> createCategoryTagsResponses(final List<Tag> tags, final List<Category> categories) {
        final Map<Category, List<Tag>> cache = initCache(categories);

        tags.forEach(tag -> {
            if (tag.getCategory() != null && cache.containsKey(tag.getCategory())) {
                cache.get(tag.getCategory()).add(tag);
            }
        });

        return cache.keySet().stream()
                .map(category -> CategoryTagsResponse.of(category, cache.get(category)))
                .collect(Collectors.toList());
    }

    private Map<Category, List<Tag>> initCache(final List<Category> categories) {
        final Map<Category, List<Tag>> cache = new HashMap<>();
        for (final Category category : categories) {
            final List<Tag> emptyTags = new ArrayList<>();
            cache.put(category, emptyTags);
        }
        return cache;
    }
}
