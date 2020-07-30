package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagSetup {

    private final TagRepository tagRepository;

    public Tag save() {
        return tagRepository.save(new Tag("태글"));
    }
}
