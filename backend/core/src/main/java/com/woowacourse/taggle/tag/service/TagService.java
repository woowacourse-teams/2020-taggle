package com.woowacourse.taggle.tag.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.CategoryTagsResponse;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final UserService userService;

    public TagResponse createTag(final SessionUser sessionUser, final TagCreateRequest tagCreateRequest) {
        final User user = userService.findById(sessionUser.getId());

        final Tag tag = tagRepository.findByName(tagCreateRequest.getName())
                .orElse(tagRepository.save(tagCreateRequest.toEntityWithUser(user)));

        return TagResponse.of(tag);
    }

    @Transactional(readOnly = true)
    public List<CategoryTagsResponse> findAllWithCategory(final SessionUser sessionUser) {
        final List<Tag> tags = tagRepository.findAllByUserId(sessionUser.getId());
        return ofTotalCategoryTagsResponses(tags);
    }

    @Transactional(readOnly = true)
    public TagBookmarkResponse findTagById(final SessionUser user, final Long tagId) {
        final Tag tag = findByIdAndUserId(tagId, user.getId());
        return TagBookmarkResponse.of(tag);
    }

    public void removeTag(final SessionUser user, final Long tagId) {
        final Tag tag = findByIdAndUserId(tagId, user.getId());
        tagRepository.delete(tag);
    }

    public List<Tag> findByCategoryId(final Long categoryId) {
        return tagRepository.findAllByCategoryId(categoryId);
    }

    public Tag findByIdAndUserId(final Long tagId, final Long userId) {
        return tagRepository.findByIdAndUserId(tagId, userId)
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다.\n"
                        + "tagId: " + tagId));
    }

    private List<CategoryTagsResponse> ofTotalCategoryTagsResponses(final List<Tag> tags) {
        final List<CategoryTagsResponse> totalCategoryTagsResponses = new ArrayList<>();

        final List<Tag> tagsWithoutCategory = tags.stream()
                .filter(tag -> tag.getCategory() == null)
                .collect(Collectors.toList());
        
        if (tagsWithoutCategory.size() > 0) {
            final CategoryTagsResponse categoryTagsResponse = CategoryTagsResponse.ofNoCategory(tagsWithoutCategory);
            totalCategoryTagsResponses.add(categoryTagsResponse);
        }

        final Map<Category, List<Tag>> cache = tags.stream()
                .filter(tag -> tag.getCategory() != null)
                .collect(Collectors.groupingBy(Tag::getCategory));

        final List<CategoryTagsResponse> categoryTagsResponses = cache.keySet().stream()
                .map(category -> CategoryTagsResponse.of(category, cache.get(category)))
                .collect(Collectors.toList());

        totalCategoryTagsResponses.addAll(categoryTagsResponses);

        return totalCategoryTagsResponses;
    }
}
