package com.woowacourse.taggle.tag.service;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagRequest;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagBookmarkResponse createTag(final TagCreateRequest tagCreateRequest) {
        Tag tag = tagRepository.findByName(tagCreateRequest.getName())
                .orElse(tagRepository.save(tagCreateRequest.toEntity()));
        return TagBookmarkResponse.of(tag);
    }

    public void removeTag(final TagRequest tagRequest) {
        Tag tag = tagRepository.findById(tagRequest.getId())
                .orElseThrow(() -> new TagNotFoundException("삭제하려는 태그가 존재하지 않습니다.\n"
                        + "tagId: " + tagRequest.getId()));
        tagRepository.delete(tag);
    }
}
