package kr.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.domain.TagBookmarkRepository;
import kr.taggle.category.domain.Category;
import kr.taggle.category.domain.CategoryRepository;
import kr.taggle.category.exception.CategoryNotFoundException;
import kr.taggle.tag.domain.Tag;
import kr.taggle.tag.domain.TagRepository;
import kr.taggle.tag.dto.TagCreateRequest;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.tag.dto.TagUpdateRequest;
import kr.taggle.tag.exception.TagNotFoundException;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {

    private final UserService userService;
    private final TagRepository tagRepository;
    private final TagBookmarkRepository tagBookmarkRepository;
    private final CategoryRepository categoryRepository;

    public TagResponse createTag(final SessionUser sessionUser, final TagCreateRequest tagCreateRequest) {
        final User user = userService.findById(sessionUser.getId());
        final Tag tag = tagRepository.findByNameAndUserId(tagCreateRequest.getName(), user.getId())
                .orElseGet(() -> tagRepository.save(tagCreateRequest.toEntityWithUser(user)));

        return TagResponse.of(tag);
    }

    public void updateTag(final SessionUser user, final TagUpdateRequest tagUpdateRequest, final Long tagId) {
        final Category category = categoryRepository.findByIdAndUserId(tagUpdateRequest.getCategoryId(), user.getId())
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다. categoryId:" + tagUpdateRequest.getCategoryId()));
        final Tag tag = findByIdAndUserId(tagId, user.getId());

        tag.updateCategory(category);
    }

    public void removeTag(final SessionUser user, final Long tagId) {
        final Tag tag = findByIdAndUserId(tagId, user.getId());
        tagBookmarkRepository.deleteAllByTagId(tag.getId());
        tagRepository.delete(tag);
    }

    public List<Tag> findByCategoryId(final Long categoryId) {
        return tagRepository.findAllByCategoryId(categoryId);
    }

    public Tag findByIdAndUserId(final Long tagId, final Long userId) {
        return tagRepository.findByIdAndUserId(tagId, userId)
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다. tagId: " + tagId));
    }

    public List<Tag> findUncategorizedTagsByUserId(final Long userId) {
        return tagRepository.findAllByUserIdAndCategoryIsNull(userId);
    }

    public List<Tag> findCategorizedTagsByUserId(final Long userId) {
        return tagRepository.findAllByUserIdAndCategoryIsNotNull(userId);
    }
}
