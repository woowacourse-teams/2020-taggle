package kr.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.taggle.bookmark.domain.TagBookmarkRepository;
import kr.taggle.tag.domain.Tag;
import kr.taggle.tag.domain.TagRepository;
import kr.taggle.tag.dto.TagCreateRequest;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.tag.exception.TagNotFoundException;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagBookmarkRepository tagBookmarkRepository;
    private final UserService userService;

    public TagResponse createTag(final SessionUser sessionUser, final TagCreateRequest tagCreateRequest) {
        final User user = userService.findById(sessionUser.getId());
        final Tag tag = tagRepository.findByName(tagCreateRequest.getName())
                .orElseGet(() -> tagRepository.save(tagCreateRequest.toEntityWithUser(user)));

        return TagResponse.of(tag);
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

    public List<Tag> findAllByUserId(final Long userId) {
        return tagRepository.findAllByUserId(userId);
    }
}
