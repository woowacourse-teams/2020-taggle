package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagSetup {

    private final TagRepository tagRepository;

    private final UserSetup userSetup;

    public Tag save() {
        User user = userSetup.save();
        return tagRepository.save(new Tag("태글", user));
    }
}
