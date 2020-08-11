package com.woowacourse.taggle.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import com.woowacourse.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final CategoryService categoryService;

    public TagResponse createTag(final SessionUser user, final TagCreateRequest tagCreateRequest) {
        final Tag tag = tagRepository.findByName(tagCreateRequest.getName())
                .orElse(tagRepository.save(tagCreateRequest.toEntity()));

        final Category category = categoryService.findByTitleAndUserId("Uncategorized", user.getId());

        tag.updateCategory(category);
        category.add(tag);
        
        return TagResponse.of(tag);
    }

    public void removeTag(final Long id) {
        final Tag tag = findById(id);
        tagRepository.delete(tag);
    }

    @Transactional(readOnly = true)
    public List<TagResponse> findTags() {
        final List<Tag> tags = tagRepository.findAll();

        return TagResponse.asList(tags);
    }

    @Transactional(readOnly = true)
    public TagBookmarkResponse findTagById(final Long id) {
        final Tag tag = findById(id);
        return TagBookmarkResponse.of(tag);
    }

    public void updateCategory(final SessionUser user, final Long tagId, final Long CategoryId) {
        final Tag tag = findById(tagId);
        final Category category = categoryService.findByIdAndUserId(user.getId(), CategoryId);

        tag.updateCategory(category);
        category.add(tag);
    }

    private Tag findById(final Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다.\n"
                        + "tagId: " + id));
    }
}
