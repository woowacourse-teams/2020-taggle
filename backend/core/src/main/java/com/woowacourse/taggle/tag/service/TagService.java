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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final CategoryService categoryService;

    public TagResponse createTag(final TagCreateRequest tagCreateRequest) {
        final Tag tag = tagRepository.findByName(tagCreateRequest.getName())
                .orElse(tagRepository.save(tagCreateRequest.toEntity()));
        return TagResponse.of(tag);
    }

    public void removeTag(final Long id) {
        final Tag tag = findById(id);
        tagRepository.delete(tag);
    }

    public List<TagResponse> findTags() {
        final List<Tag> tags = tagRepository.findAll();

        return TagResponse.asList(tags);
    }

    public TagBookmarkResponse findTagById(final Long id) {
        final Tag tag = findById(id);
        return TagBookmarkResponse.of(tag);
    }

    @Transactional
    public void updateCategory(final Long tagId, final Long CategoryId) {
        final Tag tag = findById(tagId);
        final Category category = categoryService.findById(CategoryId);

        tag.updateCategory(category);
        category.add(tag);
    }

    private Tag findById(final Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다.\n"
                        + "tagId: " + id));
    }
}
