package com.woowacourse.taggle.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagResponse createTag(final TagCreateRequest tagCreateRequest) {
        Tag tag = tagRepository.findByName(tagCreateRequest.getName())
                .orElse(tagRepository.save(tagCreateRequest.toEntity()));
        return TagResponse.of(tag);
    }

    public void removeTag(final TagRequest tagRequest) {
        Tag tag = tagRepository.findById(tagRequest.getId())
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다.\n"
                        + "tag id: " + tagRequest.getId()));
        tagRepository.delete(tag);
    }

    @Transactional(readOnly = true)
    public TagResponse findTag(final TagRequest tagRequest) {
        Tag tag = tagRepository.findById(tagRequest.getId())
                .orElseThrow(() -> new TagNotFoundException("태그가 존재하지 않습니다.\n"
                        + "tag id: " + tagRequest.getId()));
        return TagResponse.of(tag);
    }
}
