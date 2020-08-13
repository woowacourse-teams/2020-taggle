package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
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
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다."
                        + "tagId: " + tagId));
    }

    public List<Tag> findAllByUserId(final Long userId) {
        return tagRepository.findAllByUserId(userId);
    }
}
