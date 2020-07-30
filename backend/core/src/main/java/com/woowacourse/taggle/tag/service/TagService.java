package com.woowacourse.taggle.tag.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagBookmark;
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

    public TagResponse createTag(final TagCreateRequest tagCreateRequest) {
        final Tag tag = tagRepository.findByName(tagCreateRequest.getName())
                .orElse(tagRepository.save(tagCreateRequest.toEntity()));
        return TagResponse.of(tag);
    }

    public void removeTag(final Long id) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("삭제하려는 태그가 존재하지 않습니다.\n"
                        + "tagId: " + id));
        final Set<TagBookmark> bookmarks = tag.getBookmarks();
        tagRepository.delete(tag);
    }

    public List<TagResponse> findTags() {
        final List<Tag> tags = tagRepository.findAll();

        return TagResponse.asList(tags);
    }

    public TagBookmarkResponse findTagById(final Long id) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("삭제하려는 태그가 존재하지 않습니다.\n"
                        + "tagId: " + id));
        return TagBookmarkResponse.of(tag);
    }
}
